package com.gong.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.infr.entity.CampusMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (CampusMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-19 17:15:56
 */
public interface CampusMenuDao extends BaseMapper<CampusMenu> {


    @Select("SELECT * FROM `campus_menu` WHERE `id` IN \n" +
            "(SELECT `menu_id` FROM `campus_role_menu` WHERE `role_id` = \n" +
            "(SELECT `id` FROM `campus_role` WHERE `id` = \n" +
            "(SELECT `role_id` FROM `campus_user_role` WHERE `user_id` = #{id})))")
    List<CampusMenu> queryMenuByUserId(@Param("id") String id);

}

