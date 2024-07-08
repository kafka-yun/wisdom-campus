package com.gong.course.api.application.controller;

import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.service.CampusCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category/")
public class CampusCourseCategoryController {

    @Resource
    private CampusCategoryService campusCategoryService;

    @PreAuthorize("hasAuthority('sys:resource:select')")
    @GetMapping("label")
    public List<CampusCategory> queryCategoryLabel(){
        return campusCategoryService.queryCategoryLabel();
    }
}
