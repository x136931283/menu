package com.menu.redisCache.aop;

import com.menu.redisCache.annotation.RedisCache;
import com.menu.redisCache.aop.processer.RedisCacheAnnotationProcesser;
import com.menu.redisCache.aop.processer.Impl.RedisCacheAnnotationProcesserImpl;
import com.menu.redisCache.utli.SpringApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Interceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
//为目标类产生代理 指定使用cglib方式为我们的service创建代理对象，代理对象是子类。 不使用jdk代理，是因为jdk代理需要接口,不够稳定
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({SpringApplicationContextUtils.class})//确保此类一定被扫描，避免获取上下文对象时为空
@Slf4j
public class RedisCacheMethodInterceptor implements Interceptor {

    @Autowired
    private RedisCacheAnnotationProcesser processer;

    @Around("@annotation(com.menu.redisCache.annotation.RedisCache)") //环绕通知
    public Object invokeCacheAllMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("缓存拦截器开始执行...");

        //获得方法签名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获得方法对象
        Method method = signature.getMethod();
        //获取方法上的注解对象
        RedisCache annotation = AnnotationUtils.findAnnotation(method, RedisCache.class);
        if (annotation !=null){
            log.info("处理缓存信息：："+ Arrays.toString(proceedingJoinPoint.getArgs()));
            //创建处理器
            return processer.processer(proceedingJoinPoint,annotation);
        }

        //没有cache信息直接放心
        //通过反射去放行
       return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    /**
     * 清理缓存
     */
    @Around("@annotation(com.menu.redisCache.annotation.RedisCacheEvictor)") //环绕通知
    public Object invokeCacheEvictorAllMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("清理缓存...");

        //通过反射去放行
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
