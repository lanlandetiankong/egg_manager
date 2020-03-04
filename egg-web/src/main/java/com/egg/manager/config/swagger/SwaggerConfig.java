package com.egg.manager.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/16
 * \* Time: 22:13
 * \* Description:
 * \
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 可以注入多个docket，即多个版本api，可以在看到有三个版本groupName不能是重复的，v1和v2是ant风格匹配，配置文件
     * @return
     */
    @Bean
    public Docket api() {
        ParameterBuilder parameterBuilder = new ParameterBuilder() ;
        parameterBuilder.parameterType("header")
                .name("Authorization")
                .description("header中Authorization字段用于认证")
                .modelRef(new ModelRef("string"))
                .required(false).build() ;
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select()
                .apis(RequestHandlerSelectors.basePackage("com.egg.manager.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo1()).globalOperationParameters(aParameters);
    }


    private ApiInfo apiInfo1() {
        return new ApiInfoBuilder()
                .title("egg-manager后端APIs")
                .version("v0.01")
                .build();
    }


}
