package com.gong.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.course.model.dao.CampusCourseRecordingDao;
import com.gong.course.model.entity.po.CampusCourseRecording;
import com.gong.course.service.CampusCourseRecordingService;
import org.springframework.stereotype.Service;

/**
 * (CampusCourseRecording)表服务实现类
 *
 * @author makejava
 * @since 2024-05-31 16:36:40
 */
@Service("campusCourseRecordingService")
public class CampusCourseRecordingServiceImpl extends ServiceImpl<CampusCourseRecordingDao, CampusCourseRecording> implements CampusCourseRecordingService {

}

