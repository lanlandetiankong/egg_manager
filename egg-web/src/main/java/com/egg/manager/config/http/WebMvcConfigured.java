package com.egg.manager.config.http;

import com.egg.manager.config.shiro.CurrentUserAccountMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
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
                .allowedOrigins("http://localhost:8081") ;
    }
}

