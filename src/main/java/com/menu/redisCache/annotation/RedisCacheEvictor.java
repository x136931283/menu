package com.menu.redisCache.annotation;

import java.lang.annotation.*;

/**
 * redis 缓存注解清楚
 * @author Administrator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheEvictor {
    RedisCache[] value() default {};
}
