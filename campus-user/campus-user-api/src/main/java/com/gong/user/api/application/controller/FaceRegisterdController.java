package com.gong.user.api.application.controller;

import com.alibaba.fastjson.JSON;
import com.gong.commons.config.RedisLockUtils;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.commons.interfaces.ValidationGroups;
import com.gong.infr.entity.CampusUser;
import com.gong.user.model.entity.dto.FaceDto;
import com.gong.user.model.service.FaceRegisteredService;
import com.gong.user.model.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/face/")
public class FaceRegisterdController {

    @Resource
    private FaceRegisteredService faceRegisteredService;

    @Resource
    private RedisLockUtils redisLockUtils;
    @PostMapping("registered")
    @PreAuthorize("hasAnyAuthority({'user:info:update','sys:user:update'})")
    public Result<Boolean> registeredFace(@RequestBody @Validated(ValidationGroups.Update.class) FaceDto face, HttpServletRequest request){

        String token = request.getHeader("X-Token");
        if (Objects.isNull(token)) throw new CampusException("非法操作,缺少主要参数");
        String businessId  = request.getHeader("X-BusinessId");
        if (Objects.isNull(businessId)) throw new CampusException("业务id缺少,请联系管理员");
        String key = request.getHeader("X-Key");
        if (Objects.isNull(key)) throw new CampusException("业务key缺少,请联系管理员");
        log.info("FaceRegisterdController.registeredFace请求头X-Token:{}|X-BusinessId:{}|X-Key:{}",token,businessId,key);
        boolean lock = redisLockUtils.validateToken(key, businessId, token);
        System.out.println("lock = " + lock);
        if (!lock) throw new CampusException("请勿重复提交");

        CampusUser user = SecurityUtil.getUser();
        log.info("FaceLoginController.registeredFace获取用户信息:{}", JSON.toJSONString(user));
        System.out.println("user.getEmail() = " + user.getEmail());
        return faceRegisteredService.registeredFace(face.getImage(),user.getEmail());
    }

    @GetMapping("code")
    public Result<?> faceCode(){
        String id = SecurityUtil.getUser().getId();
        Boolean result = faceRegisteredService.faceCode(id);
        return Result.success(result);
    }

}
