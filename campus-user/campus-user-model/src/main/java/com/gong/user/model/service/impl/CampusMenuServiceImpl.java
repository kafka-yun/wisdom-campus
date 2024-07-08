package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.infr.dao.CampusMenuDao;
import com.gong.infr.entity.CampusMenu;
import com.gong.user.model.service.CampusMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CampusMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 17:15:56
 */
@Slf4j
@Service("campusMenuService")
public class CampusMenuServiceImpl extends ServiceImpl<CampusMenuDao, CampusMenu> implements CampusMenuService {

    @Resource
    private CampusMenuDao campusMenuDao;

    @Override
    public List<CampusMenu> queryMenuByUserId(String id) {
        log.info("CampusMenuServiceImpl.queryMenuByUserId传入用户id：{}",id);
        return campusMenuDao.queryMenuByUserId(id);
    }
}

