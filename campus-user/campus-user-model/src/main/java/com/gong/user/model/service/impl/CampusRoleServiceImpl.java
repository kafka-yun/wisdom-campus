package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.infr.dao.CampusRoleDao;
import com.gong.infr.entity.CampusRole;
import com.gong.user.model.service.CampusRoleService;
import org.springframework.stereotype.Service;

/**
 * (CampusRole)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 14:50:59
 */
@Service("campusRoleService")
public class CampusRoleServiceImpl extends ServiceImpl<CampusRoleDao, CampusRole> implements CampusRoleService {

}

