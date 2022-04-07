package com.menu.redisCache.model;

import lombok.Data;

import java.util.Objects;

/**
 * 缓存结果封装
 */
@Data
public class CacheHolder {
    private Objects value; //缓存数据
    private boolean existsCache; //缓存数据是否存在
    private Throwable throwable;
}
