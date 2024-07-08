package com.gong.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.course.model.entity.dto.CampusCourseMediaDto;
import com.gong.course.model.entity.po.CampusCourseMedia;

/**
 * (CampusCourseMedia)表服务接口
 *
 * @author makejava
 * @since 2024-05-20 17:36:20
 */
public interface CampusCourseMediaService extends IService<CampusCourseMedia> {


    CampusCourseMedia uploadMedia(CampusCourseMediaDto courseMediaDto);

    CampusCourseMedia addMediaMessage(CampusCourseMediaDto campusCourseMedia);


}

