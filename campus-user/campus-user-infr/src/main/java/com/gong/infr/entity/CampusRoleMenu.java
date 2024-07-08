package com.gong.infr.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusRoleMenu)表实体类
 *
 * @author makejava
 * @since 2024-06-19 17:08:46
 */
@Data
@SuppressWarnings("serial")
public class CampusRoleMenu extends Model<CampusRoleMenu> {
    //主键
    private String id;
    //角色id
    private String roleId;
    //菜单id
    private String menuId;
    //创建时
    private Date createTime;
    //创建人
    private String createBy;
    //修改时
    private Date updateTime;
    //修改人
    private String updateBy;

}

