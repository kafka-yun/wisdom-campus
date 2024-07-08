package com.gong.commons.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    /**
     * 通用类拷贝，弥补BeanUtils中不能拷贝List
     * */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> t) {
        List<T> resultList = new ArrayList<>();
        if (sourceList != null && t != null) {
            sourceList.forEach(sourceObject -> {
                try {
                    /*拿到传入的属性*/
                    T targetObject = t.newInstance();
                    /*拷贝类*/
                    BeanUtils.copyProperties(sourceObject, targetObject);
                    resultList.add(targetObject);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return resultList;
    }
}
