package com.menu.redisCache.utli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Primary
public class SpringApplicationContextUtils {
    private static ApplicationContext springContext;

    @Autowired
    private ApplicationContext applicationContext;
    @PostConstruct
    private void init(){
        springContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return springContext;
    }
}
