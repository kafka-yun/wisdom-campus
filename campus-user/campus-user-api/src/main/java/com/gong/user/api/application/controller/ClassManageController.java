package com.gong.user.api.application.controller;

import com.gong.commons.entity.Result;
import com.gong.commons.utils.Convert;
import com.gong.user.model.entity.dto.CampusMenuDto;
import com.gong.user.model.service.CampusMenuService;
import com.gong.user.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/class/")
public class ClassManageController {

    @Resource
    private CampusMenuService campusMenuService;


    /**
     * 根据角色返回对应的菜单标识
     * */
    @PreAuthorize("hasAuthority('sys:resource:select')")
    @GetMapping("menu")
    public Result<?> queryMenu(){
        /*获得用户id【查询对应menu】*/
        String id = SecurityUtil.getUser().getId();
        log.info("ClassManageController.queryMenu当前:{}查询菜单",id);
        return Result.success(Convert.convertList(campusMenuService.queryMenuByUserId(id), CampusMenuDto.class));
    }

}
