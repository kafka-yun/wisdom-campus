package com.gong.auth;

import cn.xfyun.api.FaceCompareClient;
import com.alibaba.fastjson.JSON;
import com.gong.auth.model.entity.xfyunResponse;
import com.gong.auth.model.mapper.CampusPermissionsDao;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.IOUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
public class AuthApplicationTest {
    @Value("${xunfei.client.appid}")
    private String appid;
    @Value("${xunfei.client.apiSecret}")
    private String apiSecret;
    @Value("${xunfei.client.apiKey}")
    private String apiKey;

    @Resource
    MinioClient minioClient;

    private String BUCKET = "face";

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



    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    CampusPermissionsDao campusPermissionsDao;

    @Test
    void passwordTest(){
        System.out.println(passwordEncoder.encode("admin"));
    }
    @Test
    void test2(){
        System.out.println(campusPermissionsDao.queryPermissionsByUserId("1001"));
    }


    @Test
    void Test3() throws Exception {
        FaceCompareClient client = new FaceCompareClient
                .Builder(appid, apiKey, apiSecret)
                .build();
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\admin\\Desktop\\a.jpg"));
        byte[] bytes = IOUtils.readFully(inputStream, -1, true);
        String images = Base64.getEncoder().encodeToString(bytes);
        String s = client.faceCompare(images, "jpg", images, "jpg");
        System.out.println("s = " + s);
        xfyunResponse response = JSON.parseObject(s, xfyunResponse.class);
        System.out.println("response = " + response);
        String decode = decode(response.getPayload().getFace_compare_result().getText());
        System.out.println("decode = " + decode);

    }
    public String decode(String base64Code) throws Exception {
        byte[] decode = Base64.getDecoder().decode(base64Code);
        String string = new String(decode, StandardCharsets.UTF_8);
        return string;
    }



}
