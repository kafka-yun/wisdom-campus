package com.gong.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.infr.entity.CampusCourse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (CampusCourse)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-20 09:46:47
 */
public interface CampusCourseDao extends BaseMapper<CampusCourse> {

    @Select("SELECT * FROM `campus_course`\n" +
            "ORDER BY  RAND()\n" +
            "LIMIT #{limit};")
    List<CampusCourse> randomQuery(Integer limit);

}

