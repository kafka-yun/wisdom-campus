package com.gong.course.api.application.controller;

import com.alibaba.fastjson.JSON;
import com.gong.commons.exception.CampusException;
import com.gong.commons.config.RedisLockUtils;
import com.gong.course.api.utils.SecurityUtil;
import com.gong.course.model.entity.dto.CampusCourseMediaDto;
import com.gong.course.model.entity.po.CampusCourseMedia;
import com.gong.course.service.CampusCourseMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping("/media")
@Slf4j
public class CampusCourseMediaController {


    @Resource
    private CampusCourseMediaService campusCourseMediaService;

    @Resource
    private RedisLockUtils redisLockUtils;
    /**
     * 课程下视频发布【需要传入创建的课程id】
     * */
    @PreAuthorize("hasAuthority('sys:course:add')")
    @PostMapping("/upload")
    public CampusCourseMediaDto uploadMedia(@RequestPart("file")MultipartFile file, @RequestPart("courseId") String courseId, HttpServletRequest request) throws IOException {

        String token = request.getHeader("X-Token");
        if (Objects.isNull(token)) throw new CampusException("非法操作,缺少主要参数");
        String businessId  = request.getHeader("X-BusinessId");
        String key = request.getHeader("X-Key");
        boolean lock = redisLockUtils.validateToken(key, businessId, token);
        if (!lock) throw new CampusException("请勿重复提交");

        CampusCourseMediaDto dto = new CampusCourseMediaDto();
        dto.setFileName(file.getOriginalFilename());


        dto.setFileType(file.getContentType());
        dto.setCourseId(courseId);
        File tempFile = File.createTempFile("minio", ".temp");
        file.transferTo(tempFile);
        /*文件路径*/
        dto.setMediaPath(tempFile.getAbsolutePath());
        dto.setUsername(SecurityUtil.getUser().getUsername());
        log.info("CampusCourseMediaController.uploadMedia初始化参数:{}", JSON.toJSONString(dto));
        CampusCourseMedia campusCourseMedia = campusCourseMediaService.uploadMedia(dto);
        BeanUtils.copyProperties(campusCourseMedia,dto);
        return dto;

    }


}
