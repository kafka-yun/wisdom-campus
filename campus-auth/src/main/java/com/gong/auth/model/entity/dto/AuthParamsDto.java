package com.gong.auth.model.entity.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AuthParamsDto {

    private String username;

    private String password;

    private String email;

    private String phone;

    private String code;

    private String checkcode;

    private String checkcodekey;

    private String authType;

    private Map<String, Object> payload = new HashMap<>();

}
