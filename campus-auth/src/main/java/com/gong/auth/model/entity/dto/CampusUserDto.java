package com.gong.auth.model.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gong.commons.interfaces.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * (CampusUser)表实体类
 *
 * @author makejava
 * @since 2024-05-07 21:36:35
 */
@Data
@SuppressWarnings("serial")
public class CampusUserDto extends Model<CampusUserDto> {
    //主键
    private String id;
    //账号
    @NotEmpty(message = "账号不能为空",groups = {ValidationGroups.Insert.class})
    @NotEmpty(message = "账号不能为空",groups = {ValidationGroups.Update.class})
    private String username;
    //密码
    @NotEmpty(message = "密码不能为空",groups = {ValidationGroups.Insert.class})
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
    @NotEmpty(message = "邮箱不能为空",groups = {ValidationGroups.Insert.class})
    private String email;

    private String qq;
    //手机号
    private String phone;


}

