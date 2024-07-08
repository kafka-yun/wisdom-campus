package com.gong.course.model.entity.po;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (CampusUser)表实体类
 *
 * @author makejava
 * @since 2024-05-07 21:36:35
 */
@Data
@SuppressWarnings("serial")
public class CampusUser extends Model<CampusUser> {
    //主键
    private String id;
    //账号
    private String username;
    //密码
    private String password;
    //加密盐
    private String salt;
    //微信unionid
    private String wxUnionid;
    //昵称
    private String nickname;
    //头像
    private String userpic;
    //组织id(学院)
    private String classId;
    //账号类型(学生/老师)
    private String utype;
    //性别(0女/1男)
    private String sex;
    //电子邮件
    private String email;

    private String qq;
    //手机号
    private String phone;
    //用户状态
    private String status;
    //创建时
    private LocalDateTime createTime;
    //修改时
    private LocalDateTime updateaTime;

}

