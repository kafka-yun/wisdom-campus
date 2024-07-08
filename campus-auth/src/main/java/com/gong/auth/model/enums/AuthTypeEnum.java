package com.gong.auth.model.enums;

import lombok.Getter;

@Getter
public enum AuthTypeEnum {

    FACE("face","人脸对比模式"),
    PASSWORD("password","密码模式"),
    WX("wx","微信扫码模式");


    private String code;

    private String doc;

    AuthTypeEnum(String code, String doc) {
        this.code = code;
        this.doc = doc;
    }

    public static  AuthTypeEnum getByCode(String code){
        for (AuthTypeEnum auth:AuthTypeEnum.values()){
            if (auth.code.equals(code)){
                return auth;
            }
        }
        return null;
    }
}
