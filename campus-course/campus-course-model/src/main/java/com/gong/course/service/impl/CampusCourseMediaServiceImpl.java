package com.gong.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.commons.exception.CampusException;
import com.gong.course.model.dao.CampusCourseMediaDao;
import com.gong.course.model.entity.dto.CampusCourseMediaDto;
import com.gong.course.model.entity.po.CampusCourseMedia;
import com.gong.course.model.entity.po.CampusUser;
import com.gong.course.service.CampusCourseMediaService;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (CampusCourseMedia)表服务实现类
 *
 * @author makejava
 * @since 2024-05-20 17:36:21
 */
@Slf4j
@Service("campusCourseMediaService")
public class CampusCourseMediaServiceImpl extends ServiceImpl<CampusCourseMediaDao, CampusCourseMedia> implements CampusCourseMediaService {


    @Resource
    MinioClient minioClient;

    @Value("${minio.bucket.course}")
    private String mediaBucket;


    @Resource
    private CampusCourseMediaService currentProxy;



    @Override
    public CampusCourseMedia uploadMedia(CampusCourseMediaDto courseMediaDto) {
        log.info("CampusCourseMediaServiceImpl.uploadMedia传入参数：{}",JSON.toJSONString(courseMediaDto));
        String mediaName = courseMediaDto.getFileName();
        System.out.println("mediaName = " + mediaName);
        /*获取文件后缀*/
        String extension = mediaName.substring(mediaName.lastIndexOf("."));
        System.out.println("extension = " + extension);
        String fileName = mediaName.substring(0,mediaName.indexOf(".mp4"));
        System.out.println("fileName = " + fileName);
        /*通过上传时间生成文件路径*/
        String defaultPath = getDefaultPath();
        System.out.println("defaultPath = " + defaultPath);
        String mediaPath = courseMediaDto.getMediaPath();
        System.out.println("mediaPath = " + mediaPath);
        /*文件md5值*/
        String fileMd5 = getFileMd5(new File(mediaPath));
        System.out.println("fileMd5 = " + fileMd5);
        /*文件名*/
        String objectName = defaultPath + fileMd5 + extension;
        System.out.println("objectName = " + objectName);

        boolean result = uploadFiles(mediaPath, courseMediaDto.getFileType(),mediaBucket, objectName);
        if (!result) throw new CampusException("上传失败");
        courseMediaDto.setFileName(objectName);
        courseMediaDto.setId(fileMd5);
        courseMediaDto.setBucket(mediaBucket);
        courseMediaDto.setTitle(fileName);
        log.info("CampusCourseMediaServiceImpl.uploadMedia传入addMessage参数:{}", JSON.toJSONString(courseMediaDto));
        CampusCourseMedia media = currentProxy.addMediaMessage(courseMediaDto);
        return media;
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

    public String getFileMd5(File file){
        try (FileInputStream InputStream = new FileInputStream(file)){
            String md5 = DigestUtils.md5Hex(InputStream);
            return md5;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getDefaultPath(){
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.format(new Date()).replace("-","/")+"/";
    }






    @Transactional
    @Override
    public CampusCourseMedia addMediaMessage(CampusCourseMediaDto campusCourseMedia){
        log.info("CampusCourseMediaServiceImpl.addMediaMessage传入参数:{}", JSON.toJSONString(campusCourseMedia));

        /*查询数据库该课程是否存在*/
        CampusCourseMedia courseMedia = lambdaQuery().eq(CampusCourseMedia::getFileName, campusCourseMedia.getFileName()).one();
        /*如果存在直接返回*/
        if (courseMedia!=null) return courseMedia;
        courseMedia = new CampusCourseMedia();
        BeanUtils.copyProperties(campusCourseMedia,courseMedia);
        courseMedia.setStatus("1");
        courseMedia.setCreateDate(LocalDateTime.now());
        courseMedia.setCreateBy(campusCourseMedia.getUsername());
        save(courseMedia);

        return courseMedia;
    }




}

