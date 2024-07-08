package com.gong.auth.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.auth.model.entity.po.CampusUser;
import com.gong.auth.model.entity.po.CampusUserRole;
import com.gong.auth.model.mapper.CampusUserDao;
import com.gong.auth.model.mapper.CampusUserRoleDao;
import com.gong.auth.model.service.RegisteredUserService;
import com.gong.commons.config.RedisUtil;
import com.gong.commons.exception.CampusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class RegisteredUserServiceImpl extends ServiceImpl<CampusUserDao,CampusUser> implements RegisteredUserService {

    private static String FACELOGINKEY = "email.code";
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CampusUserRoleDao campusUserRoleDao;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Boolean registerUser(CampusUser campusUser,String code) {
        /*初始化redis的key*/
        String key = redisUtil.buildKey(FACELOGINKEY, campusUser.getEmail());
        /*从redis中拿到value*/
        String redisValue = redisUtil.get(key);
        log.info("redis中取出的code:{}",redisValue);
        /*对比用户传入的code和redis中的code是否一致*/
        if (!redisValue.equals(code)) throw new CampusException("验证码错误");
        /*判断完立马删除redis中的code,避免用户重复使用*/
        boolean del = redisUtil.del(key);
        /*删除出错时立即抛异常*/
        if (!del) throw new CampusException("服务异常请联系管理员");
        /*通过用户名，邮箱，手机号查询用户是否存在（一个邮箱，手机号只允许注册一个用户）*/
        CampusUser user = lambdaQuery()
                .eq(CampusUser::getUsername, campusUser.getUsername())
                .eq(CampusUser::getEmail, campusUser.getEmail())
                .one();
        if (user != null) throw new CampusException("该用户已存在");
        /*用户id为uuid*/
        String userId = UUID.randomUUID().toString();
        campusUser.setId(userId);
        /*密码存入时需要进行加密*/
        campusUser.setPassword(passwordEncoder.encode(campusUser.getPassword()));
        /*用户默认状态为正常*/
        campusUser.setStatus("1");
        campusUser.setCreateTime(LocalDateTime.now());
        /*默认注册时为学生,老师端账号后续由管理员进行导入*/
        campusUser.setUtype("学生");
        boolean saveUser = save(campusUser);
        CampusUserRole role = new CampusUserRole();
        role.setId(UUID.randomUUID().toString());
        role.setRoleId("04");
        role.setUserId(userId);
        role.setCreateTime(LocalDateTime.now());
        role.setCreateBy(campusUser.getUsername());
        int saveRole = campusUserRoleDao.insert(role);
        return saveUser && (saveRole>0);
    }


}
