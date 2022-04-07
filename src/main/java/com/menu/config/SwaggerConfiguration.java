package com.menu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类.
 */

@EnableSwagger2                // Swagger的开关，表示已经启用Swagger
@Configuration                 // 声明当前配置类
public class SwaggerConfiguration {

    @Value("${swagger.basePackage}")
    private String basePackage;       // controller接口所在的包

    @Value("${swagger.title}")
    private String title;           // 当前文档的标题

    @Value("${swagger.description}")
    private String description;         // 当前文档的详细描述

    @Value("${swagger.version}")
    private String version;         // 当前文档的版本

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
                //.globalOperationParameters(globalOperation());       // 主要关注点----每个接口调用都填写token
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }
    private List<Parameter> globalOperation(){
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        /*配置请求头*/
        tokenPar.name("Authorization").description("token令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

}
