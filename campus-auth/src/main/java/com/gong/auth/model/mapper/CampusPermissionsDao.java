package com.gong.auth.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.auth.model.entity.po.CampusPermissions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (CampusPermissions)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-19 18:45:07
 */
public interface CampusPermissionsDao extends BaseMapper<CampusPermissions> {


    @Select("SELECT `permissions_code` FROM `campus_permissions` WHERE `id` IN \n" +
            "(SELECT `permissions_id` FROM `campus_role_permissions` WHERE `role_id` = \n" +
            "(SELECT `id` FROM `campus_role` WHERE `id` = \n" +
            "(SELECT `role_id` FROM `campus_user_role` WHERE `user_id` = #{id})))")
    List<String> queryPermissionsByUserId(@Param("id") String id);


}

