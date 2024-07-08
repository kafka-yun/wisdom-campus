package com.gong.auth.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.mapper.CampusUserDao;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.service.CampusUserService;
import com.gong.commons.entity.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * (CampusUser)表服务实现类
 *
 * @author makejava
 * @since 2024-05-07 21:36:35
 */
@Service("campusUserService")
public class CampusUserServiceImpl extends ServiceImpl<CampusUserDao, CampusUser> implements CampusUserService {

}

