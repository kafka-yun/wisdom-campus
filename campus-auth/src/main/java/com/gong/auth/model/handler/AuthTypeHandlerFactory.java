package com.gong.auth.model.handler;

import com.gong.auth.model.enums.AuthTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthTypeHandlerFactory implements InitializingBean {


    @Resource
    private List<AuthService> authServiceList;

    private Map<AuthTypeEnum,AuthService> handler  = new HashMap<>();


    public AuthService getHandler(String authType){
        AuthTypeEnum authTypeEnum = AuthTypeEnum.getByCode(authType);
        return handler.get(authTypeEnum);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        authServiceList.forEach(h->{
            handler.put(h.getAuthType(),h);
        });
    }
}
