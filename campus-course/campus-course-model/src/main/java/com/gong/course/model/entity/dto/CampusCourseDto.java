package com.gong.course.model.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (CampusCourse)表实体类
 *
 * @author makejava
 * @since 2024-05-20 09:46:48
 */
@Data
@SuppressWarnings("serial")
public class CampusCourseDto extends Model<CampusCourseDto> {
    //主键id
    private String id;
    //课程名
    private String courseName;
    //标题
    private String title;
    //介绍
    private String introduce;
    //价格
    private BigDecimal price;
    //预览图片
    private String previewImage;
    //组织id
    private String companyId;





}

