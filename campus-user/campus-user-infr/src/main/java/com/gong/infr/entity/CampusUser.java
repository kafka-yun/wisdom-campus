package com.gong.infr.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * (CampusUser)表实体类
 *
 * @author makejava
 * @since 2024-05-27 14:59:09
 */
@Data
@SuppressWarnings("serial")
public class CampusUser extends Model<CampusUser> {

    //主键
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String id;
    //账号
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String username;
    //密码
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String password;
    //加密盐
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String salt;
    //微信unionid
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String wxUnionid;
    //昵称
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String nickname;
    //头像
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String userpic;
    //组织id(学院)
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String classId;
    //账号类型(学生/老师)
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String utype;
    //性别(0女/1男)
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String sex;
    //电子邮件
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String email;
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String qq;
    //手机号
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String phone;
    //用户状态
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private String status;
    //创建时
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private LocalDateTime createTime;
    //修改时
    @TableField(insertStrategy = FieldStrategy.DEFAULT,updateStrategy = FieldStrategy.DEFAULT)
    private LocalDateTime updateaTime;

}

