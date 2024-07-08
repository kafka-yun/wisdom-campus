package com.gong.auth.application.api.controller;

import com.gong.commons.entity.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/auth/")
public class UserLogoutController {

    @Resource
    private TokenStore tokenStore;

    @PostMapping("logout")
    public Result<?> logout(@RequestHeader("Authorization")String authorization){
        if (authorization != null && authorization.startsWith("bearer ")){
            try {
                String token = authorization.replace("bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
                tokenStore.removeAccessToken(accessToken);
                return Result.success();
            }catch (Exception e){
                e.printStackTrace();
                return Result.fail();
            }
        }
        return Result.fail();
    }

}
