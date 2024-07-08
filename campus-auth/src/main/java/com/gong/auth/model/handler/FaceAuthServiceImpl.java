package com.gong.auth.model.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.entity.dto.AuthParamsDto;
import com.gong.auth.model.entity.dto.CampusUserExt;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.enums.AuthTypeEnum;
import com.gong.auth.model.mapper.CampusUserDao;
import com.gong.commons.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class FaceAuthServiceImpl extends ServiceImpl<CampusUserDao, CampusUser> implements AuthService{

    private static String FACELOGINKEY = "face.login";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public AuthTypeEnum getAuthType() {
        return AuthTypeEnum.FACE;
    }

    @Override
    public CampusUserExt execute(AuthParamsDto authParamsDto) {
        /*从redis中获取值*/
        String key = redisUtil.buildKey(FACELOGINKEY, authParamsDto.getUsername());
        String loginCode = redisUtil.get(key);
        if (!authParamsDto.getCode().equals(loginCode)) throw new RuntimeException("登录码错误请重新验证");
        /*删除redis中的登录码,防止用户防止用户重复使用*/
        redisUtil.del(key);
        CampusUser user = lambdaQuery()
                .eq(CampusUser::getUsername, authParamsDto.getUsername())
                .one();
        if (Objects.isNull(user)) throw new RuntimeException("系统异常,请联系管理员！");
        CampusUserExt userExt = new CampusUserExt();
        BeanUtils.copyProperties(user,userExt);
        return userExt;
    }
}
