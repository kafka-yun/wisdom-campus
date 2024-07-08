package com.gong.user.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.infr.entity.CampusUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (CampusUser)表服务接口
 *
 * @author makejava
 * @since 2024-05-27 14:59:12
 */
public interface CampusUserService extends IService<CampusUser> {


    CampusUser updateInformation(CampusUser user, MultipartFile image);
    CampusUser updateUser(CampusUser user);


}

