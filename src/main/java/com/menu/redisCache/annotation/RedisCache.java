package com.menu.redisCache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 声明式缓存注解
 * @author Administrator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
    String region();
    String value();
    long time() default  1;
    TimeUnit timeUnit() default TimeUnit.DAYS;
}
