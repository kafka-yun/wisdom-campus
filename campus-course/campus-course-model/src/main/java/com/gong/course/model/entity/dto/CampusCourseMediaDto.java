package com.gong.course.model.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gong.course.model.entity.po.CampusCourseMedia;
import lombok.Data;

import java.util.Date;

/**
 * (CampusCourseMedia)表实体类
 *
 * @author makejava
 * @since 2024-05-20 17:36:19
 */
@Data
@SuppressWarnings("serial")
public class CampusCourseMediaDto extends CampusCourseMedia {

    private String username;


}

