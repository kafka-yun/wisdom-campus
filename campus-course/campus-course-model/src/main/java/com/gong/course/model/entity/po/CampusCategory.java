package com.gong.course.model.entity.po;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (CampusCategory)表实体类
 *
 * @author makejava
 * @since 2024-06-12 16:34:48
 */
@SuppressWarnings("serial")
@Data
public class CampusCategory extends Model<CampusCategory> {
    //主键
    private String id;
    //分类名称
    private String name;
    //标签
    private String label;
    //课程id
    private String resourceId;
    //父id
    private String parentId;
    //状态
    private Long status;
    //创建人
    private String createBy;
    //创建时
    private LocalDateTime createTime;
    //修改人
    private String updataBy;
    //修改时
    private LocalDateTime updateTime;
    @TableField(select = false)
    private List<CampusCategory> categoryList;

}

