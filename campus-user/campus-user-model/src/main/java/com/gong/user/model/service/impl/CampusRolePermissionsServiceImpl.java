package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.infr.dao.CampusRolePermissionsDao;
import com.gong.infr.entity.CampusRolePermissions;
import com.gong.user.model.service.CampusRolePermissionsService;
import org.springframework.stereotype.Service;

/**
 * (CampusRolePermissions)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 17:15:01
 */
@Service("campusRolePermissionsService")
public class CampusRolePermissionsServiceImpl extends ServiceImpl<CampusRolePermissionsDao, CampusRolePermissions> implements CampusRolePermissionsService {

}

