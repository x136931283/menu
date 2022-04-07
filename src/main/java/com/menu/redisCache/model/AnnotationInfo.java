package com.menu.redisCache.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.lang.annotation.Annotation;

/**
 * 缓存数据封装
 * @author Administrator
 */
@Data
public class AnnotationInfo <T extends Annotation>  {

    private T annotation;
    private String region;
    private String key;//方法名:region:value

    @Override
    public String toString() {
        if (annotation == null) {
            return null;
        }
        return JSONObject.toJSONString(this);
    }
}
