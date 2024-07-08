package com.gong.auth.model.handler;

import com.gong.auth.model.entity.dto.AuthParamsDto;
import com.gong.auth.model.entity.dto.CampusUserExt;
import com.gong.auth.model.enums.AuthTypeEnum;

public interface AuthService {

    AuthTypeEnum getAuthType();


    CampusUserExt execute(AuthParamsDto authParamsDto);




}
