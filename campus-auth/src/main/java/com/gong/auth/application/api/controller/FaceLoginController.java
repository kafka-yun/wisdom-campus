package com.gong.auth.application.api.controller;

import com.gong.auth.model.entity.dto.FaceDto;
import com.gong.auth.model.service.FaceContrastService;
import com.gong.commons.entity.Result;
import com.gong.commons.interfaces.ValidationGroups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/face/")
public class FaceLoginController {

    @Resource
    private FaceContrastService faceContrastService;

    /**
     * 人脸登录（需传入人脸图片，邮箱）用于校验
     * */
    @PostMapping("contrast")
    public Result<Map<String,String>> faceContrast(@RequestBody @Validated(ValidationGroups.Insert.class) FaceDto face){
        log.info("FaceLoginController.registeredFace接收邮箱:{}",face.getEmail());
        return faceContrastService.faceContrast(face.getImage(),face.getEmail());
    }






}
