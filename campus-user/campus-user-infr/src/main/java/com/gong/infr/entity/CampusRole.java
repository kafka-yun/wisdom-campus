package com.gong.infr.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusRole)表实体类
 *
 * @author makejava
 * @since 2024-06-19 14:50:59
 */
@Data
@SuppressWarnings("serial")
public class CampusRole extends Model<CampusRole> {
    //主键
    private String id;
    //角色名
    private String roleName;
    //角色标识
    private String roleCode;
    //角色状态
    private String roleStatus;
    //创建时
    private Date createTime;
    //修改时
    private Date updateTime;
    //创建人
    private String createBy;
    //修改人
    private String updateBy;


}

