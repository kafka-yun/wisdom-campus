package com.gong.course;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gong.course.model.dao.CampusCategoryDao;
import com.gong.course.model.dao.CampusCourseDao;
import com.gong.course.model.dao.CampusCourseRecordingDao;
import com.gong.course.model.entity.dto.CampusCategoryDto;
import com.gong.course.model.entity.dto.CampusCourseDto;
import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.model.entity.po.CampusCourse;
import com.gong.course.model.entity.po.CampusCourseMedia;
import com.gong.course.model.entity.po.CampusCourseRecording;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class CourseApplicationTest {

    @Resource
    private CampusCourseRecordingDao campusCourseRecordingDao;


    @Resource
    private CampusCategoryDao campusCategoryDao;
    @Resource
    private CampusCourseDao campusCourseDao;

    @Test
    void CourseCountTest(){
        Integer integer = campusCourseRecordingDao.selectCount(new LambdaQueryWrapper<CampusCourseRecording>().eq(CampusCourseRecording::getCourseid, "1001"));
        System.out.println("integer = " + integer);
    }


    @Test
    void JoinTest(){
        List<CampusCategory> categories = campusCategoryDao.selectList(null);
        HashMap<String, CampusCategory> map = new HashMap<>();
        ArrayList<CampusCategory> campusCategories = new ArrayList<>();
        categories.forEach(o->{
            map.put(o.getId(),o);
        });
        System.out.println("map = " + map);
        categories.forEach(o->{
            CampusCategory category = map.get(o.getParentId());
            if (Objects.isNull(category)){
                campusCategories.add(o);
            }else {
                if (category.getCategoryList()==null){
                    category.setCategoryList(new ArrayList<>());
                }
                category.getCategoryList().add(o);
            }
        });

    }

    @Test
    void CopyTest(){
        List<CampusCourse> list = campusCourseDao.selectList(null);
        List<CampusCourseDto> campusCourseDtos = convertList(list, CampusCourseDto.class);
        System.out.println("campusCourseDtos = " + campusCourseDtos);
    }

    @Test
    void SubStringTest(){
        String sub = "vue.jpg";
        int i = sub.indexOf(".");
        System.out.println("i = " + i);
        String substring = sub.substring(0,i);
        System.out.println("substring = " + substring);
        String fileType = sub.substring(sub.indexOf("."));
        System.out.println("fileType = " + fileType);
    }

    @Test
    void RandomQuery(){

        List<CampusCourse> records = campusCourseDao.randomQuery(3);
        System.out.println("records = " + records);

    }


    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> t) {
        List<T> resultList = new ArrayList<>();
        if (sourceList != null && t != null) {
            sourceList.forEach(sourceObject -> {
                try {
                    T targetObject = t.newInstance(); // Create a new instance of the target class
                    BeanUtils.copyProperties(sourceObject, targetObject); // Correctly copy properties
                    resultList.add(targetObject);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace(); // Handle the exceptions appropriately
                }
            });
        }
        return resultList;
    }






}
