package com.gong.auth.model.service.impl;

import com.gong.auth.model.service.EmailService;
import com.gong.commons.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private static String FACELOGINKEY = "email.code";
    @Resource
    private RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public Boolean sandEmailCode(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String code = String.valueOf(((Math.random()*9+1)*100000));
            code = code.substring(0,code.indexOf('.'));
            message.setSubject("智慧教育先锋");
            message.setFrom(from);
            message.setTo(email);
            message.setText(buildContent(code+""));
            javaMailSender.send(message);
            log.info("发送验证码:{}",code);
            String key = redisUtil.buildKey(FACELOGINKEY, email);
            redisUtil.set(key,code,3L,TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("邮箱一次:{}",e.getMessage());
            return false;
        }

    }

    /**
     * 读取邮件模板
     * 替换模板中的信息
     *
     * @param title 内容
     * @return
     */
    public String buildContent(String title) {
        //加载邮件html模板
        org.springframework.core.io.Resource resource = new ClassPathResource("mailtemplate.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }

}
