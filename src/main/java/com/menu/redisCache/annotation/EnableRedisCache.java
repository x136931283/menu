package com.menu.redisCache.annotation;

import com.menu.redisCache.aop.RedisCacheMethodInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启缓存注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisCacheMethodInterceptor.class})//引入拦截器
public @interface EnableRedisCache {
}
