package com.gong.infr.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (CampusCourse)表实体类
 *
 * @author makejava
 * @since 2024-05-20 09:46:48
 */
@Data
@SuppressWarnings("serial")
public class CampusCourse extends Model<CampusCourse> {
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
    //状态
    private String status;
    //创建时
    private LocalDateTime createData;
    //修改时
    private LocalDateTime updateData;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;



}

