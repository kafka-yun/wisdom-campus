package com.gong.auth.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.auth.model.entity.po.CampusUser;

public interface RegisteredUserService extends IService<CampusUser> {

    Boolean registerUser(CampusUser campusUser,String code);

}
