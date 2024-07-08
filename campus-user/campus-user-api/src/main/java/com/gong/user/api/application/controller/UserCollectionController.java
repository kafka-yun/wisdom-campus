package com.gong.user.api.application.controller;

import com.gong.commons.annotation.RateLimit;
import com.gong.commons.entity.Result;
import com.gong.infr.entity.CampusCourse;
import com.gong.infr.entity.CampusUserCourseCollection;
import com.gong.user.model.service.CampusUserCourseCollectionService;
import com.gong.user.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collection/")
public class UserCollectionController {

    @Resource
    private CampusUserCourseCollectionService campusUserCourseCollectionService;

    @GetMapping("course/{courseId}")
    @RateLimit(time = 60,count = 4,message = "你到底要干嘛")
    public Result<?> CollectionCourse(@PathVariable("courseId") String courseId,Boolean code){
        String userId = SecurityUtil.getUser().getId();
        log.info("UserCollectionController.CollectionCourse传入值:{},操作码:{}",courseId,code);
        boolean result = false;
        String message = null;
        String type = null;
        if (code){
            result = campusUserCourseCollectionService.addCollectionCourseById(userId, courseId);
            message = "收藏成功";
            type = "success";
        }else {
            result = campusUserCourseCollectionService.deleteCollectionCourseById(userId,courseId);
            message = "取消收藏";
            type = "warning";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("message",message);
        map.put("type",type);
        map.put("code",result);
        return Result.success(map);
    }

    @GetMapping("query/{courseId}")
    public Result<?> queryCollectionCourse(@PathVariable("courseId") String courseId){
        String userId = SecurityUtil.getUser().getId();
        return Result.success(campusUserCourseCollectionService.queryCollectionCourseById(courseId,userId));
    }
    @GetMapping("/querys")
    public Result<?> queryCollectionCourses(){
        String userId = SecurityUtil.getUser().getId();
        List<CampusCourse> courses = campusUserCourseCollectionService.queryCollectionCoursesById(userId);
        return Result.success(courses);
    }


}
