package com.gong.auth.model.service;

import com.gong.commons.entity.Result;

import java.util.Map;

public interface FaceContrastService {


    Result<Map<String,String>> faceContrast(String base64, String email);



}
