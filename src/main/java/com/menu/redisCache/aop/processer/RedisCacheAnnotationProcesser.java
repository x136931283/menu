package com.menu.redisCache.aop.processer;


import com.menu.redisCache.annotation.RedisCache;
import com.menu.redisCache.aop.processer.Impl.RedisCacheAnnotationProcesserImpl;
import com.menu.redisCache.utli.SpringApplicationContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;

public interface RedisCacheAnnotationProcesser {

    /**
     * 抽象方法，处理缓存操作
     * @param proceedingJoinPoint
     * @return
     */
    Object processer(ProceedingJoinPoint proceedingJoinPoint,RedisCache redisCache);

}
