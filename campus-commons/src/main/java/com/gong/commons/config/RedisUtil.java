package com.gong.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String CACHE_KEY_SEPARATOR = ".";

    /*构建缓存key*/

    public String buildKey(String... strings){
        return Stream.of(strings).collect(Collectors.joining(CACHE_KEY_SEPARATOR));
    }

    /*是否存在key*/

    public boolean exist(String key){
        return redisTemplate.hasKey(key);
    }

    /*是否删除*/

    public boolean del(String key){
        return redisTemplate.delete(key);
    }

    /*set（不带过期时间）*/

    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, String value, Long time, TimeUnit timeUnit){
         redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }

    /*set（带过期时间）*/

    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,time,timeUnit);
    }

    /*获取string类型缓存*/

    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Boolean zAdd(String key,String value,Long score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    public Long countZset(String key){
        return redisTemplate.opsForZSet().size(key);
    }

    public Set<String> rangeZset(String key, long start, long end){
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    public Long removeZset(String key,Object value){
        return redisTemplate.opsForZSet().remove(key,value);
    }

    public void removeZsetList(String key,Set<String> value){
        value.stream().forEach(v->{
            redisTemplate.opsForZSet().remove(key,v);
        });
    }

    public Double score(String key,Object value){
        return redisTemplate.opsForZSet().score(key,value);
    }

    public Set<String> rangeByScore(String key,long start,long end){
        return redisTemplate.opsForZSet().rangeByScore(key,Double.valueOf(String.valueOf(start)),Double.valueOf(String.valueOf(end)));
    }

    public Object addScore(String key,Object obj,double score){
        return redisTemplate.opsForZSet().incrementScore(key,obj,score);
    }


    public Object rank(String key,Object obj){
        return redisTemplate.opsForZSet().rank(key,obj);
    }

}
