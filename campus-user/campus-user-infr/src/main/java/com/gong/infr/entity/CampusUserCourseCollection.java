package com.gong.infr.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (CampusUserCourseCollection)表实体类
 *
 * @author makejava
 * @since 2024-06-26 11:14:37
 */
@SuppressWarnings("serial")
public class CampusUserCourseCollection extends Model<CampusUserCourseCollection> {
    //主键
    private String id;
    //课程id
    private String courseId;
    //用户id
    private String userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }

