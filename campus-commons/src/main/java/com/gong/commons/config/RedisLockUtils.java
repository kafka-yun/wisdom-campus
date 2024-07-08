package com.gong.commons.config;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtils {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 生成token并存储到redis中
     * key用于区分业务类型
     * businessId 业务id，不然订单id
     * */
    public String generateAndSaveToken(String key,String businessId){
        String token = UUID.randomUUID().toString();

        String buildKey = redisUtil.buildKey(key, ":", businessId);
        redisUtil.del(buildKey);
        /*将token存入redis中相当于加锁*/
        redisUtil.set(buildKey,token,1L, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 验证Token是否有效
     * @param key Token前缀
     * @param businessId 业务ID
     * @param token 用户传递的Token
     * @return 是否有效
     */
    public boolean validateToken(String key,String businessId,String token){
        /*生成key*/
        String buildKey = redisUtil.buildKey(key, ":", businessId);
        /*拿到token*/
        String tokenLock = redisUtil.get(buildKey);
        System.out.println("tokenLock = " + tokenLock);
        /*判断token是否有效*/
        if (tokenLock!=null&&tokenLock.equals(token)){
            /*判断有效相当于取锁）*/
            redisUtil.del(buildKey);
            return true;
        }
        return false;
    }





}
