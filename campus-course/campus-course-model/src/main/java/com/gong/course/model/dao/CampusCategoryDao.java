package com.gong.course.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.course.model.entity.po.CampusCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (CampusCategory)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-12 16:34:48
 */
public interface CampusCategoryDao extends BaseMapper<CampusCategory> {


    @Select(" SELECT * FROM `campus_category` a\n" +
            " JOIN `campus_category` b\n" +
            " ON a.`id` = b.`parent_id`\n" +
            " ")
    List<CampusCategory> queryJoinCategory();


}

