package com.gong.auth.model.entity.po;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (CampusUserRole)表实体类
 *
 * @author makejava
 * @since 2024-06-19 17:30:10
 */
@Data
@SuppressWarnings("serial")
public class CampusUserRole extends Model<CampusUserRole> {
    //主键
    private String id;
    //用户id
    private String userId;
    //角色id
    private String roleId;
    //创建时
    private LocalDateTime createTime;
    //创建人
    private String createBy;
    //修改时
    private LocalDateTime updateTime;
    //修改人
    private String updateBy;



}

