package com.gong.course.model.entity.po;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * (CampusCourseMedia)表实体类
 *
 * @author makejava
 * @since 2024-05-20 17:36:19
 */
@Data
@SuppressWarnings("serial")
public class CampusCourseMedia extends Model<CampusCourseMedia> {
    //主键
    private String id;
    //课程id
    private String courseId;
    //媒资路径
    private String mediaPath;
    //文件类型
    private String fileType;
    //文件名
    private String fileName;
    //桶
    private String bucket;
    /*标题*/
    private String title;
    //媒资状态
    private String status;
    //创建时
    private LocalDateTime createDate;
    //修改时
    private LocalDateTime updateDate;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;



}

