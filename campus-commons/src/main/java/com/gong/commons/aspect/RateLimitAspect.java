package com.gong.commons.aspect;

import com.gong.commons.annotation.RateLimit;
import com.gong.commons.exception.CampusException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Aspect
@Component
public class RateLimitAspect {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Around("@annotation(rateLimit)")
    public Object doAround(ProceedingJoinPoint joinPoint, RateLimit rateLimit)throws Throwable{
        String key = rateLimit.key();
        if (StringUtils.isEmpty(key)) {
            // 如果注解中的key为空，则使用方法签名生成key
            key = joinPoint.getSignature().toString();
        }

        long currentTimeMillis = System.currentTimeMillis();
        long expireTime = currentTimeMillis + rateLimit.timeUnit().toMillis(rateLimit.time());

        // 使用Redis的原子操作尝试增加访问计数
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        if (increment == null || increment > rateLimit.count()) {
            // 超过限制，抛出自定义异常或直接返回错误信息
            throw new CampusException(rateLimit.message());
        } else {
            // 更新key的过期时间，确保时间窗口内的计数不会累积到下一个窗口
            redisTemplate.expireAt(key, new Date(expireTime));
        }

        return joinPoint.proceed();
    }

}
