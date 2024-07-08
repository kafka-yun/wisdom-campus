package com.gong.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.course.model.dao.CampusCourseDao;
import com.gong.course.model.dao.CampusCourseMediaDao;
import com.gong.course.model.dao.CampusCourseRecordingDao;
import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.model.entity.po.CampusCourse;
import com.gong.course.model.entity.po.CampusCourseMedia;
import com.gong.course.model.entity.po.CampusCourseRecording;
import com.gong.course.service.CampusCourseService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sound.midi.Receiver;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * (CampusCourse)表服务实现类
 *
 * @author makejava
 * @since 2024-05-20 09:46:49
 */
@Slf4j
@Service("campusCourseService")
public class CampusCourseServiceImpl extends ServiceImpl<CampusCourseDao, CampusCourse> implements CampusCourseService {

    @Resource
    private CampusCourseRecordingDao campusCourseRecordingDao;

    @Resource
    private CampusCourseMediaDao campusCourseMediaDao;

    @Resource
    private CampusCourseService campusCourseService;

    @Resource
    private CampusCourseDao campusCourseDao;

    @Resource
    private MinioClient minioClient;

    private final String BUKET = "course";

    @Value("${minio.endpoint}")
    private String endpoint;

    @Override
    public List<CampusCourse> queryAllCourse() {

        return lambdaQuery()
                .ne(CampusCourse::getStatus, "0")
                .eq(CampusCourse::getCompanyId,"0")
                .list();
    }

    /**
     * 用户点击课程详细（监听用户行为）
     */
    @Override
    public Result<Map<String, Object>> queryCourseById(String id, String userId) {
        log.info("CampusCourseServiceImpl.queryCourseById传入参数,courseId:{},userId:{}", id, userId);
        /*查询课程是否存在*/
        CampusCourse course = lambdaQuery()
                .eq(CampusCourse::getId, id)
                .one();
        if (Objects.isNull(course)) throw new CampusException("课程查询失败,请联系管理员修改");
        /*通过课程号查询课程媒资*/
        List<CampusCourseMedia> media = campusCourseMediaDao
                .selectList(new LambdaQueryWrapper<CampusCourseMedia>()
                        .eq(CampusCourseMedia::getCourseId, id));
        /*记录用户点击课程*/
        CampusCourseRecording recording = new CampusCourseRecording();
        recording.setId(UUID.randomUUID().toString());
        recording.setCourseid(id.toString());
        recording.setUserid(userId);
        recording.setCreateTime(LocalDateTime.now());
        boolean status = campusCourseService.addAccessRecords(recording);
        if (!status) throw new CampusException("系统异常，请联系管理员");
        Integer count = campusCourseRecordingDao.selectCount(new LambdaQueryWrapper<CampusCourseRecording>().eq(CampusCourseRecording::getCourseid, id));
        HashMap<String, Object> result = new HashMap<>();
        result.put("course", course);
        result.put("media", media);
        result.put("count", count);
        return Result.success(result);
    }

    @Override
    @Transactional
    public boolean addAccessRecords(CampusCourseRecording recording) {
        return campusCourseRecordingDao.insert(recording) > 0;
    }

    @Override
    public List<CampusCourse> queryCourseByIds(List<CampusCategory> categoryList) {
        log.info("CampusCourseServiceImpl.queryCourseByIds传入参数:{}", JSON.toJSONString(categoryList));
        ArrayList<String> ids = new ArrayList<>();
        categoryList.forEach(o -> {
            ids.add(o.getResourceId());
        });
        System.out.println("ids = " + ids);
        List<CampusCourse> result = lambdaQuery().in(CampusCourse::getId, ids).list();
        System.out.println("result = " + result);
        return result;
    }

    @Override
    public List<CampusCourse> queryCourseByLike(String like) {
        log.info("CampusCourseServiceImpl.queryCourseByLike传入参数:{}", like);
        return lambdaQuery()
                .like(CampusCourse::getCourseName, like)
                .or()
                .like(CampusCourse::getTitle, like)
                .or()
                .like(CampusCourse::getIntroduce, like)
                .list();
    }

    @Override
    @SneakyThrows(Exception.class)
    public Result<?> uploadCourse(CampusCourse course, MultipartFile image) {
        log.info("CampusCourseServiceImpl.uploadCourse传入参数:{}", JSON.toJSONString(course));
        /*创建临时文件*/
        File tempFile = File.createTempFile("minio", ".temp");
        image.transferTo(tempFile);

        String filename = image.getOriginalFilename();
        /*文件名*/
        String imageName = filename.substring(0, filename.indexOf("."));
        /*文件类型*/
        String fileType = filename.substring(filename.indexOf("."));
        /*文件路径*/
        String localPath = tempFile.getAbsolutePath();
        /*生成文件存储路径*/
        String remotePath = getDefaultPath();
        /*文件md5值*/
        String fileMd5 = getFileMd5(new File(localPath));
        /*minio中的文件名*/
        String objectName = remotePath + fileMd5 + fileType;
        /*将文件上传到minio*/
        boolean upload = uploadFiles(localPath, image.getContentType(), BUKET, objectName);
        if (!upload) throw new CampusException("上传失败");

        /*生成课程id*/
        String id = UUID.randomUUID().toString();
        course.setId(id);
        /*拼接图片地址*/
        String previewImage = endpoint + "/" + BUKET + "/" + objectName;
        course.setPreviewImage(previewImage);

        boolean save = campusCourseService.addCampusCourse(course);

        return Result.success(upload&&save);
    }

    @Transactional
    @Override
    public boolean addCampusCourse(CampusCourse course) {
        course.setStatus("1");
        course.setCreateData(LocalDateTime.now());
        return save(course);
    }

    /**
     * 随机查询三条进行推荐
     * */
    @Override
    public List<CampusCourse> randomQueryCourse() {
        return campusCourseDao.randomQuery(3);
    }

    @Override
    public Integer queryToDayDotCounting() {
        return campusCourseRecordingDao.queryToDayDotCounting();
    }

    public boolean uploadFiles(String localPath, String minioType, String bucket, String objectName) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .contentType(minioType)
                    .filename(localPath)
                    .build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFileMd5(File file) {
        try (FileInputStream InputStream = new FileInputStream(file)) {
            String md5 = DigestUtils.md5Hex(InputStream);
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDefaultPath() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.format(new Date()).replace("-", "/") + "/";
    }


}

