package com.gong.auth.model.service.impl;

import cn.xfyun.api.FaceCompareClient;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.application.rpc.RemoteFaceContrast;
import com.gong.auth.model.entity.decode;
import com.gong.auth.model.entity.dto.RpcFaceMessage;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.entity.xfyunResponse;
import com.gong.auth.model.mapper.CampusUserDao;
import com.gong.auth.model.service.FaceContrastService;
import com.gong.auth.utils.EncryptUtil;
import com.gong.commons.config.RedisLockUtils;
import com.gong.commons.config.RedisUtil;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.IOUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FaceContrastServiceImpl extends ServiceImpl<CampusUserDao, CampusUser> implements FaceContrastService {

    private static String FACELOGINKEY = "face.login";

    private static final String FACE_BUCKET = "face";
    @Resource
    private MinioClient minioClient;


    @Resource
    private RedisUtil redisUtil;

    @Value("${xunfei.client.appid}")
    private String appid;
    @Value("${xunfei.client.apiSecret}")
    private String apiSecret;
    @Value("${xunfei.client.apiKey}")
    private String apiKey;
    private final String BUCKET = "face";
    private FaceCompareClient initFaceClient(){
        return new FaceCompareClient
                .Builder(appid,apiKey,apiSecret)
                .build();
    }

    @SneakyThrows(Exception.class)
    private InputStream getObject(String bucket, String objectName){
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(objectName)
                        .build()
        );
    }

    @SneakyThrows(Exception.class)
    @Override
    public Result<Map<String, String>> faceContrast(String base64, String email) {
        /*查询用户是否存在*/
        CampusUser user = lambdaQuery()
                .eq(CampusUser::getEmail, email)
                .one();
        if (Objects.isNull(user)) throw new CampusException("该邮箱不存在，请重新输入");
        if (Objects.isNull(user.getUserpic())) throw new CampusException("该用户未录入人脸,请使用密码模式登录录入密码");
        /*进行人脸比对*/
        boolean contrast = base64FaceContrast(base64, user.getUserpic());
        /*获取加密结果*/
        if (!contrast) throw new CampusException("人脸比对失败,请重试！");
        /*使用用户名生成唯一标识存入redis中人脸登录时使用*/
        String faceLoginKey = redisUtil.buildKey(FACELOGINKEY, user.getUsername());
        String code = UUID.randomUUID().toString();
        redisUtil.set(faceLoginKey, code, 3L, TimeUnit.MINUTES);
        HashMap<String, String> data = new HashMap<>();
        /*将登录码发送至前端用户登录判断*/
        data.put("username", user.getUsername());
        data.put("code", code);
        return Result.success(data);
    }

     private boolean base64FaceContrast(String base64Image, String remoteImagePath) throws Exception {
        FaceCompareClient client = initFaceClient();
        if (base64Image.contains(",")) base64Image = base64Image.split(",")[1];
        byte[] zero = Base64.getDecoder().decode(base64Image);
        /*远程minio图片*/
        byte[] remoteByte = IOUtils.readFully(getObject(BUCKET,remoteImagePath), -1, true);
        String local = Base64.getEncoder().encodeToString(zero);
        String remote = Base64.getEncoder().encodeToString(remoteByte);
        String contrast = client.faceCompare(local, "jpg", remote, "jpg");

        /*获取结果*/
        xfyunResponse response = JSON.parseObject(contrast, xfyunResponse.class);
        String result = response.getPayload().getFace_compare_result().getText();
        /*对比结果*/
        String code = decode(result);
        System.out.println("code:" + code);
        decode decode = JSON.parseObject(code,decode.class);
        System.out.println("decode = " + decode);
        /*对比成功返回加密字符ture否则返回加密字符false*/
        if (decode.getScore()>=0.990) {
            log.info("FaceContrastServiceImpl.faceContrast对比成功");
            return true;
        }
        return false;
    }
    private String decode(String base64Code) throws Exception {
        byte[] decode = Base64.getDecoder().decode(base64Code);
        String string = new String(decode, StandardCharsets.UTF_8);
        return string;
    }
}







