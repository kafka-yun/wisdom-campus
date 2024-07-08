package com.gong.auth.model.entity.po;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (CampusPermissions)表实体类
 *
 * @author makejava
 * @since 2024-06-19 18:45:08
 */
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    public String getPermissionsCode() {
        return permissionsCode;
    }

    public void setPermissionsCode(String permissionsCode) {
        this.permissionsCode = permissionsCode;
    }

    public String getPermissionsStatus() {
        return permissionsStatus;
    }

    public void setPermissionsStatus(String permissionsStatus) {
        this.permissionsStatus = permissionsStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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

