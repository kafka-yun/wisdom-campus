package com.gong.infr.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusMenu)表实体类
 *
 * @author makejava
 * @since 2024-06-19 17:15:56
 */
@Data
@SuppressWarnings("serial")
public class CampusMenu extends Model<CampusMenu> {
    //主键
    private String id;
    //菜单父id
    private String menuParentId;
    //菜单名
    private String menuName;
    //菜单英文name
    private String menuCode;
    //vue-router路径
    private String menuPath;
    //创建时
    private Date createTime;
    //修改时
    private Date updateTime;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;

}

