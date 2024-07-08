package com.gong.user.api.application.controller;

import com.alibaba.fastjson.JSON;
import com.gong.commons.config.RedisUtil;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.infr.entity.CampusUser;
import com.gong.user.model.entity.dto.CampusUserDto;
import com.gong.user.model.service.CampusUserService;
import com.gong.user.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/info/")
public class UserInfoController {

    @Resource
    private RedisUtil redisUtil;

    private final static String EMAILKEY = "email.code";

    @Resource
    private CampusUserService campusUserService;

    @PreAuthorize("hasAnyAuthority({'user:info:update','sys:user:update'})")
    @PostMapping("update/info/{code}")
    public Result<?> updateUserInformation(@RequestPart(value = "image",required = false)MultipartFile image, CampusUserDto user,
                                           @PathVariable("code")String code){

        log.info("UserInfoController.updateUserInformation传入值:{}", JSON.toJSONString(user));
        String key = redisUtil.buildKey(EMAILKEY, user.getEmail());
        /*获取redis中的code*/
        String remoteCode = redisUtil.get(key);
        if (!remoteCode.equals(code)) throw new CampusException("验证码错误请重试");
        redisUtil.del(key);
        /*获取当前登录的用户名*/
        String username = SecurityUtil.getUser().getUsername();
        CampusUser userInfo = new CampusUser();
        user.setUsername(username);
        BeanUtils.copyProperties(user,userInfo);
        CampusUser result = campusUserService.updateInformation(userInfo, image);
        return Result.success(result);
    }

}
