package com.gong.infr.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusPermissions)表实体类
 *
 * @author makejava
 * @since 2024-06-19 14:51:28
 */
@Data
@SuppressWarnings("serial")
public class CampusPermissions extends Model<CampusPermissions> {
    //主键
    private String id;
    //权限名
    private String permissionsName;
    //权限标识
    private String permissionsCode;
    //权限状态
    private String permissionsStatus;
    //创建时
    private Date createTime;
    //修改时
    private Date updateTime;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;

}

