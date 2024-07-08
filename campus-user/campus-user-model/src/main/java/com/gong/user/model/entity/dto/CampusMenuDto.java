package com.gong.user.model.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (CampusMenu)表实体类
 *
 * @author makejava
 * @since 2024-06-19 17:15:56
 */
@Data
@SuppressWarnings("serial")
public class CampusMenuDto extends Model<CampusMenuDto> {
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

}

