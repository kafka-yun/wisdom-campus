package com.gong.user.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.infr.entity.CampusCourse;
import com.gong.infr.entity.CampusUserCourseCollection;

import java.util.List;

/**
 * (CampusUserCourseCollection)表服务接口
 *
 * @author makejava
 * @since 2024-06-26 11:14:37
 */
public interface CampusUserCourseCollectionService extends IService<CampusUserCourseCollection> {

    List<CampusCourse> queryCollectionCoursesById(String userId);


    CampusUserCourseCollection queryCollectionCourseById(String courseId,String userId);


    boolean addCollectionCourseById(String userId,String courseId);

    boolean deleteCollectionCourseById(String userId,String courseId);

}

