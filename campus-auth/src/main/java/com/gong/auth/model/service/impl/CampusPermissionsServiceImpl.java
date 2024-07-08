package com.gong.auth.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.mapper.CampusPermissionsDao;
import com.gong.auth.model.entity.po.CampusPermissions;
import com.gong.auth.model.service.CampusPermissionsService;
import org.springframework.stereotype.Service;

/**
 * (CampusPermissions)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 18:45:08
 */
@Service("campusPermissionsService")
public class CampusPermissionsServiceImpl extends ServiceImpl<CampusPermissionsDao, CampusPermissions> implements CampusPermissionsService {

}

