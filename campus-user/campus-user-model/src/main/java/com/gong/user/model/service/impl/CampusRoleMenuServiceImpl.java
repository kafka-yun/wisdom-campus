package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.infr.dao.CampusRoleMenuDao;
import com.gong.infr.entity.CampusRoleMenu;
import com.gong.user.model.service.CampusRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * (CampusRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 17:08:46
 */
@Service("campusRoleMenuService")
public class CampusRoleMenuServiceImpl extends ServiceImpl<CampusRoleMenuDao, CampusRoleMenu> implements CampusRoleMenuService {

}

