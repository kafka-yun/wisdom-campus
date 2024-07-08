package com.gong.user.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.commons.exception.CampusException;
import com.gong.infr.dao.CampusCourseDao;
import com.gong.infr.dao.CampusUserCourseCollectionDao;
import com.gong.infr.entity.CampusCourse;
import com.gong.infr.entity.CampusUserCourseCollection;
import com.gong.user.model.service.CampusUserCourseCollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * (CampusUserCourseCollection)表服务实现类
 *
 * @author makejava
 * @since 2024-06-26 11:14:40
 */
@Service("campusUserCourseCollectionService")
public class CampusUserCourseCollectionServiceImpl extends ServiceImpl<CampusUserCourseCollectionDao, CampusUserCourseCollection> implements CampusUserCourseCollectionService {


    @Resource
    private CampusCourseDao campusCourseDao;

    @Override
    public List<CampusCourse> queryCollectionCoursesById(String userId) {
        List<CampusUserCourseCollection> ids = lambdaQuery().eq(CampusUserCourseCollection::getUserId, userId).list();
        ArrayList<CampusCourse> result = new ArrayList<>();
        ids.forEach(i->{
            CampusCourse course = campusCourseDao.selectById(i.getCourseId());
            result.add(course);
        });
        return result;
    }

    @Override
    public CampusUserCourseCollection queryCollectionCourseById(String courseId,String userId) {
        return lambdaQuery()
                .eq(CampusUserCourseCollection::getCourseId,courseId)
                .eq(CampusUserCourseCollection::getUserId,userId)
                .one();
    }
    @Transactional
    @Override
    public boolean addCollectionCourseById(String userId, String courseId) {

        CampusUserCourseCollection query =  lambdaQuery()
                .eq(CampusUserCourseCollection::getCourseId,courseId)
                .eq(CampusUserCourseCollection::getUserId,userId).one();

        if (query!=null) throw new CampusException("请勿重复提交");

        CampusUserCourseCollection collection = new CampusUserCourseCollection();

        collection.setId(UUID.randomUUID().toString());
        collection.setUserId(userId);
        collection.setCourseId(courseId);

        return save(collection);
    }

    @Transactional
    @Override
    public boolean deleteCollectionCourseById(String userId, String courseId) {
        LambdaQueryWrapper<CampusUserCourseCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CampusUserCourseCollection::getCourseId,courseId);
        wrapper.eq(CampusUserCourseCollection::getUserId,userId);
        CampusUserCourseCollection query = baseMapper.selectOne(wrapper);

        if (query  == null) throw new CampusException("该收藏列表为空");

        return baseMapper.delete(wrapper)>0;
    }
}

