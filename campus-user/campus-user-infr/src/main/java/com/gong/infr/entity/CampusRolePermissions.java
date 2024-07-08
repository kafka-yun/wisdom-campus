package com.gong.infr.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusRolePermissions)表实体类
 *
 * @author makejava
 * @since 2024-06-19 17:15:01
 */
@Data
@SuppressWarnings("serial")
public class CampusRolePermissions extends Model<CampusRolePermissions> {
    //id
    private String id;
    //角色id
    private String roleId;
    //权限id
    private String permissionsId;
    //创建时
    private Date creartTime;
    //修改时
    private Date updateTime;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;

}

