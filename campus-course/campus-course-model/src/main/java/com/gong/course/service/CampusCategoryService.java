package com.gong.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.course.model.entity.dto.CampusCategoryDto;
import com.gong.course.model.entity.po.CampusCategory;

import java.util.List;

/**
 * (CampusCategory)表服务接口
 *
 * @author makejava
 * @since 2024-06-12 16:34:48
 */
public interface CampusCategoryService extends IService<CampusCategory> {


    List<CampusCategory> queryCategoryLabel();


}

