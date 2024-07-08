package com.gong.course.model.entity.po;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusCourseRecording)表实体类
 *
 * @author makejava
 * @since 2024-05-31 16:36:35
 */
@SuppressWarnings("serial")
@Data
public class CampusCourseRecording extends Model<CampusCourseRecording> {
    //主键
    private String id;
    //用户id
    private String userid;
    //课程id
    private String courseid;
    //创建时
    private LocalDateTime createTime;

}

