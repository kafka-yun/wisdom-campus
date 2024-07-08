package com.gong.course.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.course.model.entity.po.CampusCourseRecording;
import org.apache.ibatis.annotations.Select;

/**
 * (CampusCourseRecording)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-31 16:36:33
 */
public interface CampusCourseRecordingDao extends BaseMapper<CampusCourseRecording> {

    @Select("SELECT COUNT(*) FROM `campus_course_recording` WHERE TO_DAYS(`create_time`) = TO_DAYS(NOW());")
    Integer queryToDayDotCounting();


}

