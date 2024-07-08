package com.gong.auth.application.api.controller;

import com.alibaba.fastjson.JSON;
import com.gong.auth.model.entity.dto.CampusUserDto;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.service.EmailService;
import com.gong.auth.model.service.RegisteredUserService;
import com.gong.commons.annotation.RateLimit;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.commons.interfaces.ValidationGroups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/registered/")
public class RegisteredUserController {


    @Resource
    private RegisteredUserService registeredUserService;

    @Resource
    private EmailService emailService;

    /**
     * 用户注册
     * */
    @PostMapping("user/{code}")
    public Result<Boolean> registeredUser(@RequestBody @Validated(ValidationGroups.Insert.class) CampusUserDto campusUserDto,@PathVariable("code") String code){
        log.info("RegisteredUserController.registeredUser传入参数:{},code:{}", JSON.toJSONString(campusUserDto),code);
        CampusUser user = new CampusUser();
        BeanUtils.copyProperties(campusUserDto,user);
        Boolean result = registeredUserService.registerUser(user,code);
        return Result.success(result);
    }

    /**
     * 验证码发送
     * */
    @GetMapping("send/{email}")
    @RateLimit(time = 60,count = 3,message = "访问过于频繁:What the fuck are you going to do！")
    public Result<Boolean> sandEmailCode(@PathVariable("email") String email){
        log.info("RegisteredUserController.sandEmailCode接收邮箱号:{}",email);
        Boolean send = emailService.sandEmailCode(email);
        if (!send)throw new CampusException("邮箱发送异常,请重试!");
        return Result.success(send);
    }


}
