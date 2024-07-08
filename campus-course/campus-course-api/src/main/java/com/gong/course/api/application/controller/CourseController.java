package com.gong.course.api.application.controller;


import com.alibaba.fastjson.JSON;
import com.gong.commons.annotation.RateLimit;
import com.gong.commons.entity.Result;
import com.gong.commons.exception.CampusException;
import com.gong.commons.utils.Convert;
import com.gong.commons.config.RedisLockUtils;
import com.gong.course.api.utils.SecurityUtil;
import com.gong.course.model.entity.dto.CampusCourseDto;
import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.model.entity.po.CampusCourse;
import com.gong.course.model.entity.po.CampusUser;
import com.gong.course.service.CampusCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/course/")
public class CourseController {

    @Resource
    private CampusCourseService campusCourseService;

    @Resource
    private RedisLockUtils redisLockUtils;


    /**
     * 拿锁后才可以操作课程发布
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @PostMapping("addLock")
    @RateLimit(time = 60,count = 3,message = "你越界了哦老弟")
    public Result<String> addLock(@RequestParam("key")String key,@RequestParam("BusniessId") String busniessId){
        log.info("CourseController.addLock传入key:{},业务id:{}",key,busniessId);
        String token = redisLockUtils.generateAndSaveToken(key, busniessId);
        return Result.success(token);
    }


    /**
     * 课程查询
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @GetMapping("content")
    public List<CampusCourseDto> queryCourse() {
        return Convert.convertList(campusCourseService.queryAllCourse(),CampusCourseDto.class);
    }

    /**
     * 查询课程详细并记录用户点击课程
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @GetMapping("query/{id}")
    public Result<Map<String, Object>> queryCourseById(@PathVariable("id") String id) {
        log.info("CourseController.queryCourseById传入参数ID:{}", id);
        CampusUser user = SecurityUtil.getUser();
        return campusCourseService.queryCourseById(id, user.getId());
    }

    /**
     * 查询分类标签下的课程
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @PostMapping("queryByIds")
    public List<CampusCourseDto> queryCourseByIds(@RequestBody List<CampusCategory> categoryList) {
        log.info("CourseController.queryCourseByIds传入参数:{}", JSON.toJSONString(categoryList));
        /*判断传入的分类是否有空值是则返回空*/
        if (Objects.isNull(categoryList)) return new ArrayList<>();
        return Convert.convertList(campusCourseService.queryCourseByIds(categoryList),CampusCourseDto.class);
    }

    /**
     * 课程搜索模糊查询
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @PostMapping("search")
    public List<CampusCourseDto> queryCourseByLike(@RequestParam("search") String search) {
        log.info("CourseController.queryCourseByLike传入参数:{}", search);
        /*判断传入值*/
        if (StringUtils.isEmpty(search)) return null;
        return Convert.convertList(campusCourseService.queryCourseByLike(search), CampusCourseDto.class);
    }

    /**
     *
     * 课程创建【需要拿到锁后才可以请求该接口】
     * */
    @PreAuthorize("hasAuthority('sys:course:add')")
    @PostMapping("uploadCourse")
    public Result<?> uploadCourse(CampusCourseDto campusCourseDto, @RequestPart("image")MultipartFile image, HttpServletRequest request){

        String token = request.getHeader("X-Token");
        if (Objects.isNull(token)) throw new CampusException("非法操作,缺少主要参数");
        String businessId  = request.getHeader("X-BusinessId");
        String key = request.getHeader("X-Key");
        boolean lock = redisLockUtils.validateToken(key, businessId, token);
        if (!lock) throw new CampusException("请勿重复提交");

        log.info("CourseController.uploadCourse传入参数:{}", JSON.toJSONString(campusCourseDto));
        /*获取当前登录用户信息*/
        CampusUser user = SecurityUtil.getUser();
        CampusCourse course = new CampusCourse();
        BeanUtils.copyProperties(campusCourseDto,course);
        /*设置组织id*/
        course.setCompanyId(user.getClassId());
        /*设置创建人*/
        course.setCreateBy(user.getUsername());
        return campusCourseService.uploadCourse(course,image);
    }
    /**
     * 首页课程推荐
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @GetMapping("random")
    public List<CampusCourseDto> randomQueryCourse(){
        return Convert.convertList(campusCourseService.randomQueryCourse(),CampusCourseDto.class);
    }

    @PreAuthorize("hasAnyAuthority('sys:background:select')")
    @GetMapping("Counting")
    public Result<?> queryToDayDotCounting(){
        return Result.success(campusCourseService.queryToDayDotCounting());
    }




}
