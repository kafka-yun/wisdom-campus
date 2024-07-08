package com.gong.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.commons.entity.Result;
import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.model.entity.po.CampusCourse;
import com.gong.course.model.entity.po.CampusCourseRecording;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * (CampusCourse)表服务接口
 *
 * @author makejava
 * @since 2024-05-20 09:46:49
 */
public interface CampusCourseService extends IService<CampusCourse> {


    List<CampusCourse> queryAllCourse();

    Result<Map<String,Object>> queryCourseById(String id, String userId);

    boolean addAccessRecords(CampusCourseRecording recording);

    List<CampusCourse> queryCourseByIds(List<CampusCategory> categoryList);

    List<CampusCourse> queryCourseByLike(String like);


    Result<?> uploadCourse(CampusCourse course, MultipartFile image);

    boolean addCampusCourse(CampusCourse course);

    List<CampusCourse> randomQueryCourse();

    Integer queryToDayDotCounting();


}

