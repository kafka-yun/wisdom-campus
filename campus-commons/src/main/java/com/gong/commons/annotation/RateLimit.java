package com.gong.commons.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {


    // 限流key，默认使用方法名

    String key() default "";

    // 限流时间窗口大小，单位秒

    int time() default 1;

    // 限流次数

    int count() default 2;

    // 时间单位，默认秒

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    // 超出限流时的提示信息

    String message() default "请求过于频繁，请稍后再试";

}
