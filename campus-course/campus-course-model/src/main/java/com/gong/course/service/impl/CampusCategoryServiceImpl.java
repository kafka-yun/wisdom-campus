package com.gong.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.commons.config.RedisLockUtils;
import com.gong.course.model.dao.CampusCategoryDao;
import com.gong.course.model.entity.dto.CampusCategoryDto;
import com.gong.course.model.entity.po.CampusCategory;
import com.gong.course.service.CampusCategoryService;
import org.apache.ibatis.javassist.expr.NewExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * (CampusCategory)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 16:34:52
 */
@Service("campusCategoryService")
public class CampusCategoryServiceImpl extends ServiceImpl<CampusCategoryDao, CampusCategory> implements CampusCategoryService {


    /**
    * 树形结构（课程标签）
    * */

    @Override
    public List<CampusCategory> queryCategoryLabel() {
        List<CampusCategory> categoryList = query().list();
        LinkedHashMap<String, CampusCategory> map = new LinkedHashMap<>();
        ArrayList<CampusCategory> result = new ArrayList<>();
        categoryList.forEach(o->{
            map.put(o.getId(),o);
        });
        categoryList.forEach(o->{
            CampusCategory category = map.get(o.getParentId());
            if (Objects.isNull(category)){
                result.add(o);
            }else {
                if (Objects.isNull(category.getCategoryList())){
                    category.setCategoryList(new ArrayList<>());
                }
                category.getCategoryList().add(o);
            }

        });
        return result;
    }
}

