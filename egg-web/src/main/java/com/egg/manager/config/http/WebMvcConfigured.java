package com.egg.manager.config.http;

import com.egg.manager.common.base.props.build.deploy.DeployConfProps;
import com.egg.manager.config.shiro.CurrentUserAccountMethodArgumentResolver;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 17:04
 * \* Description:
 * \
 */
@Configuration
public class WebMvcConfigured extends WebMvcConfigurationSupport{

    @Autowired
    private DeployConfProps deployConfProps;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //方法注入增强 Resolver
        argumentResolvers.add(getCurrentUserAccountMethodArgumentResolver()) ;
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public CurrentUserAccountMethodArgumentResolver getCurrentUserAccountMethodArgumentResolver(){
        return new CurrentUserAccountMethodArgumentResolver() ;
    }


    /**
     * 添加跨域请求
     * allowCredentials 是否携带jsessionid
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/egg_manager/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowCredentials(true)
                .allowedOrigins(deployConfProps.getAllowedOrigins()) ;
    }

    /**
     * 添加 swagger-ui 等的文件路径映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }


}

