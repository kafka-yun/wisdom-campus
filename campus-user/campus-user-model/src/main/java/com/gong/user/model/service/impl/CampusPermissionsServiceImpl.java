package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.infr.dao.CampusPermissionsDao;
import com.gong.infr.entity.CampusPermissions;
import com.gong.user.model.service.CampusPermissionsService;
import org.springframework.stereotype.Service;

/**
 * (CampusPermissions)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 14:51:28
 */
@Service("campusPermissionsService")
public class CampusPermissionsServiceImpl extends ServiceImpl<CampusPermissionsDao, CampusPermissions> implements CampusPermissionsService {

}

