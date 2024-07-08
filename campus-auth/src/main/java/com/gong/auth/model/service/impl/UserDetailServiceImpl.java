package com.gong.auth.model.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.entity.dto.AuthParamsDto;
import com.gong.auth.model.entity.dto.CampusUserExt;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.handler.AuthService;
import com.gong.auth.model.handler.AuthTypeHandlerFactory;
import com.gong.auth.model.mapper.CampusPermissionsDao;
import com.gong.auth.model.mapper.CampusUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl extends ServiceImpl<CampusUserDao,CampusUser> implements UserDetailsService {


    @Resource
    private AuthTypeHandlerFactory authTypeHandlerFactory;


    @Resource
    private CampusPermissionsDao campusPermissionsDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*将接收到的json转为对象形式*/
        AuthParamsDto auth = null;
        log.info("UserDetailServiceImpl.loadUserByUsername接收参数:{}",username);
        try {
            auth = JSON.parseObject(username, AuthParamsDto.class);
        }catch (Exception e){
            throw new RuntimeException("请求认证参数不符合要求");
        }
        String authType = auth.getAuthType();
        AuthService handler = authTypeHandlerFactory.getHandler(authType);
        CampusUserExt execute = handler.execute(auth);
        return getUserPrincipal(execute);
    }

    public UserDetails getUserPrincipal(CampusUserExt u){
        String password = u.getPassword();

        /*获取权限信息*/
        List<SimpleGrantedAuthority> permissions = campusPermissionsDao
                .queryPermissionsByUserId(u.getId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        u.setPassword(null);
        String userJson = JSON.toJSONString(u);

        return User.withUsername(userJson)
                .password(password)
                .authorities(permissions)
                .build();
    }

}
