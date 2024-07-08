package com.gong.auth.model.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.entity.dto.AuthParamsDto;
import com.gong.auth.model.entity.dto.CampusUserExt;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.enums.AuthTypeEnum;
import com.gong.auth.model.mapper.CampusUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class PasswordAuthServiceImpl  extends ServiceImpl<CampusUserDao, CampusUser> implements AuthService{

    @Resource
    PasswordEncoder passwordEncoder;


    @Override
    public AuthTypeEnum getAuthType() {
        return AuthTypeEnum.PASSWORD;
    }

    @Override
    public CampusUserExt execute(AuthParamsDto authParamsDto) {
        log.info("PasswordAuthServiceImpl.execute接收参数:{}", JSON.toJSONString(authParamsDto));
        /*TODO:验证码接收*/

        CampusUser user = lambdaQuery()
                .eq(CampusUser::getUsername, authParamsDto.getUsername())
                .one();
        if (user == null) throw  new RuntimeException("该用户不存在");
        if (!passwordEncoder.matches(authParamsDto.getPassword(),user.getPassword())) throw new RuntimeException("密码输入错误,请重试!");
        CampusUserExt campusUserExt = new CampusUserExt();
        BeanUtils.copyProperties(user,campusUserExt);
        return campusUserExt;
    }

//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        List<Integer> zz =  list;
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        System.out.println(zz);
//
//        List<Integer> subList = new ArrayList<>(list.subList(0, 1));
//        list.add(4);
//        System.out.println(subList);
//        System.out.println(list);
//
//        Map<String, String > map = new HashMap<>();
//        map.put("a", "1");
//        map.put("b", "2");
//
//        Set<String> keys = map.keySet();
//
//
//
//        map.put("c", "3");
//        System.out.println(keys);
//    }
}
