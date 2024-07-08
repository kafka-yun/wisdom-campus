package com.gong.auth.application.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
public class TokenConfig {

    private String SIGNING_KEY = "gong_cheng_yun";

    @Resource
    TokenStore tokenStore;


    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean(name = "authorizationServerTokenServicesCustom")
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices service = new DefaultTokenServices();
        service.setSupportRefreshToken(true);
        service.setTokenStore(tokenStore);
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);
        service.setAccessTokenValiditySeconds(7200);
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }


}
