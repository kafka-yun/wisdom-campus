package com.gong.auth.model.handler;

import com.gong.auth.model.entity.dto.AuthParamsDto;
import com.gong.auth.model.entity.dto.CampusUserExt;
import com.gong.auth.model.enums.AuthTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class WxAuthServiceImpl implements AuthService{
    @Override
    public AuthTypeEnum getAuthType() {
        return AuthTypeEnum.WX;
    }

    @Override
    public CampusUserExt execute(AuthParamsDto authParamsDto) {
        return null;
    }

}
