package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.commons.exception.CampusException;
import com.gong.infr.dao.CampusUserDao;
import com.gong.infr.entity.CampusUser;
import com.gong.user.model.service.CampusUserService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (CampusUser)表服务实现类
 *
 * @author makejava
 * @since 2024-05-27 14:59:14
 */
@Slf4j
@Service("campusUserService")
public class CampusUserServiceImpl extends ServiceImpl<CampusUserDao, CampusUser> implements CampusUserService {
    @Resource
    MinioClient minioClient;

    private final String BUKETE = "yun";

    @Resource
    CampusUserService campusUserService;
    @Override
    public CampusUser updateInformation(CampusUser user, MultipartFile image) {

        if (image!=null){
            /*文件类型*/
            String fileType = image.getContentType();
            System.out.println("fileType = " + fileType);
            /*文件路径*/
            String imagePath = null;
            /*源文件名*/
            String filename = image.getOriginalFilename();
            System.out.println("filename = " + filename);

            try {
                File tempFile = File.createTempFile("minio", ".temp");
                image.transferTo(tempFile);
                imagePath = tempFile.getAbsolutePath();
                System.out.println("imagePath = " + imagePath);
            } catch (IOException e) {
                log.error("CampusUserServiceImpl.updateInformation异常",e);
                throw new CampusException("文件上传失败,请联系管理员");
            }
            /*获取文件后缀*/
            String extension = filename.substring(filename.lastIndexOf("."));
            System.out.println("extension = " + extension);
            /*文件名*/
            String substring = filename.substring(0, filename.indexOf(extension));
            System.out.println("substring = " + substring);
            /*通过上传事件生成路径*/
            String defaultPath = getDefaultPath();
            System.out.println("defaultPath = " + defaultPath);
            /*md5值*/
            String fileMd5 = getFileMd5(new File(imagePath));
            /*文件名*/
            String objectName = defaultPath + fileMd5 + extension;
            System.out.println("objectName = " + objectName);
            user.setSalt(BUKETE+"/"+objectName);
            log.info("文件类型：{},源文件名:{},临时文件路径:{},文件名:{},",fileType,filename,imagePath,objectName);
            boolean upload = uploadFiles(imagePath, fileType, BUKETE, objectName);
            if (!upload) throw new CampusException("文件上传失败");
        }

        return campusUserService.updateUser(user);
    }

    @Transactional
    @Override
    public CampusUser updateUser(CampusUser user){
        user.setUpdateaTime(LocalDateTime.now());
        LambdaUpdateWrapper<CampusUser> eq = new LambdaUpdateWrapper<CampusUser>().eq(CampusUser::getUsername, user.getUsername());
        boolean update = update(user,eq);
        if (!update) throw new CampusException("修改失败,请稍后重试");
        return user;
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

}

