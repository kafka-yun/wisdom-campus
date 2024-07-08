package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.infr.dao.CampusUserDao;
import com.gong.infr.entity.CampusUser;
import com.gong.user.model.service.FaceRegisteredService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class FaceRegisteredServiceImpl implements FaceRegisteredService {

    @Resource
    private CampusUserDao campusUserDao;


    private static final String FACE_BUCKET = "face";

    @Resource
    private MinioClient minioClient;




    @Override
    @Transactional
    public Result<Boolean> registeredFace(String base64, String email) {
        log.info("FaceRegisteredServiceImpl.registeredFace传入参数邮箱信息:{}",email);
        CampusUser user = campusUserDao.selectOne(
                new LambdaQueryWrapper<CampusUser>()
                        .eq(CampusUser::getEmail, email));
        /*通过邮箱查看用户是否存在*/

        if (Objects.isNull(user)) throw new CampusException("该邮箱不存在，请重新输入");
//        if (!Objects.isNull(user.getUserpic())) throw new CampusException("该用户人脸信息已存在");
        Path tempFile = null;
        base64 = base64.split(",")[1];
        try {
            byte[] decode = Base64.getDecoder().decode(base64);
            tempFile = Files.createTempFile("minio", ".temp");
            Files.copy(new ByteArrayInputStream(decode), tempFile, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String defaultPath = getDefaultPath();
        String fileMd5 = getFileMd5(tempFile.toFile());
        String objectName = defaultPath + fileMd5 + ".jpg";
        Path path = tempFile.toAbsolutePath();
        boolean uploadStatus = uploadFiles(path.toString(), "image/jpeg", FACE_BUCKET, objectName);
        if (uploadStatus) {
            /*上传成功后更新用户信息*/
            user.setUserpic(objectName);
            int result = campusUserDao.updateById(user);
            if (!(result>=0)) throw new CampusException("上传失败");
        }
        return Result.success(uploadStatus);
    }

    @Override
    public Boolean faceCode(String userId) {
        CampusUser user = campusUserDao.selectOne(new LambdaQueryWrapper<CampusUser>().eq(CampusUser::getId, userId));
        return Objects.isNull(user.getUserpic());
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
