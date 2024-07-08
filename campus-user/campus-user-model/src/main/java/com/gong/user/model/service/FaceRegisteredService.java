package com.gong.user.model.service;

import com.gong.commons.entity.Result;

public interface FaceRegisteredService {

    Result<Boolean> registeredFace(String base64, String email);


    Boolean faceCode(String userId);

}

