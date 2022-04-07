package com.menu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        //1、添加配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行那些原始域
        config.addAllowedOrigin("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行请求方式
        config.addAllowedMethod("*");
        //放行头部信息
        config.addAllowedHeader("*");

        //2、添加映射路径
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",config);

        //3、返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

}
