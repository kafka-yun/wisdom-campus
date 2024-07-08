package com.gong.user.utils;

import com.alibaba.fastjson.JSON;
import com.gong.infr.entity.CampusUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static CampusUser getUser(){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("SecurityUtil.getUser:{}",JSON.toJSONString(principal));
            if (principal instanceof String){
                String s = principal.toString();
                CampusUser user = JSON.parseObject(s, CampusUser.class);
                return user;
            }
        }catch (Exception e){
            log.error("获取当前登录用户信息失败:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
