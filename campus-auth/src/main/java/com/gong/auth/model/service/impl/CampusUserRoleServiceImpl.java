package com.gong.auth.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.mapper.CampusUserRoleDao;
import com.gong.auth.model.entity.po.CampusUserRole;
import com.gong.auth.model.service.CampusUserRoleService;
import org.springframework.stereotype.Service;

/**
 * (CampusUserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 17:30:10
 */
@Service("campusUserRoleService")
public class CampusUserRoleServiceImpl extends ServiceImpl<CampusUserRoleDao, CampusUserRole> implements CampusUserRoleService {

}

