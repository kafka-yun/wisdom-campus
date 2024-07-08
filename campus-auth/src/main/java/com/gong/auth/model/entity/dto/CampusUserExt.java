package com.gong.auth.model.entity.dto;

import com.gong.auth.model.entity.po.CampusUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CampusUserExt extends CampusUser {

    List<String> permission = new ArrayList<>();

}
