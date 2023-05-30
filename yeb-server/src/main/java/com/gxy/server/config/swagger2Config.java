package com.gxy.server.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
/*
* Swagger2配置
* */
@Configuration
@EnableSwagger2
public class swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当下包下的controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.gxy.server.controller"))
                .paths(PathSelectors.any())
                .build()
                // 添加登录认证
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    //设置文档信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("云E办接口文档")
                .contact(new Contact("xxxx","http:localhost:8081/doc.html","xxxx@xxxx.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes(){
        //设置请求头信息
        List<ApiKey> results = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        results.add(apiKey);
        return results;
    }

    private List<SecurityContext> securityContexts(){
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }

}
