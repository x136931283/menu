package com.menu.redisCache.aop.processer.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.menu.pojo.Result;
import com.menu.redisCache.annotation.RedisCache;
import com.menu.redisCache.aop.processer.RedisCacheAnnotationProcesser;
import com.menu.redisCache.model.AnnotationInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

@Service
@Slf4j
public class RedisCacheAnnotationProcesserImpl implements RedisCacheAnnotationProcesser {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Object processer(ProceedingJoinPoint proceedingJoinPoint, RedisCache redisCache) {

        //构建AnnotationInfo
        AnnotationInfo<RedisCache> redisCacheAnnotationInfo = annotationInfo(proceedingJoinPoint, redisCache);

        //获取缓存数据
        String redisData = redisTemplate.opsForValue().get(redisCacheAnnotationInfo.getKey());
        if (!StringUtils.isEmpty(redisData)) {
            log.info("击中缓存::" + redisCacheAnnotationInfo.getKey());
            JSONObject jsonObject = JSON.parseObject(redisData, JSONObject.class);
            return Result.ok(jsonObject);
        }
        Object data = null;
        try {
            log.info("接口获取::" + redisCacheAnnotationInfo.getKey());
            data =  proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            //保存到redis
            redisTemplate.opsForValue().set(redisCacheAnnotationInfo.getKey(),JSONObject.toJSONString(JSONObject.toJSON(data)),redisCache.time(),redisCache.timeUnit());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("拦截异常！！");
        }

        return data;
    }

    private AnnotationInfo<RedisCache> annotationInfo(ProceedingJoinPoint proceedingJoinPoint, RedisCache redisCache){

        AnnotationInfo<RedisCache> annotationInfo = new AnnotationInfo<>();
        annotationInfo.setAnnotation(redisCache);
        annotationInfo.setRegion(redisCache.region());
        annotationInfo.setKey(getAnnotationInfoKey(proceedingJoinPoint,redisCache));
        return annotationInfo;
    }

    private String getAnnotationInfoKey(ProceedingJoinPoint proceedingJoinPoint, RedisCache redisCache){
        //获得类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获得方法名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        //获得动态参数
        StringBuilder stringBuilder = new StringBuilder("");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            String s = JSONObject.toJSONString(arg);
            stringBuilder.append(s);
        }
        //拼接返回
        return className+":"+methodName+":"+ stringBuilder.toString().hashCode();
    }
}
