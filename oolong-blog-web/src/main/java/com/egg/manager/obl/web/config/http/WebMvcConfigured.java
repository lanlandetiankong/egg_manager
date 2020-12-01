package com.egg.manager.obl.web.config.http;

import com.egg.manager.persistence.commons.base.props.build.deploy.DeployConfProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Configuration
public class WebMvcConfigured extends WebMvcConfigurationSupport {

    @Autowired
    private DeployConfProps deployConfProps;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //方法注入增强 Resolver
        //argumentResolvers.add(getCurrentUserAccountMethodArgumentResolver());
        //argumentResolvers.add(getPageBeanMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /*@Bean
    public CurrentUserAccountMethodArgumentResolver getCurrentUserAccountMethodArgumentResolver() {
        return new CurrentUserAccountMethodArgumentResolver();
    }

    @Bean
    public PageBeanMethodArgumentResolver getPageBeanMethodArgumentResolver() {
        return new PageBeanMethodArgumentResolver();
    }*/


    /**
     * 添加跨域请求
     * allowCredentials 是否携带jsessionid
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/oolong_blog/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowCredentials(true)
                .allowedOrigins(deployConfProps.getAllowedOrigins());
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
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

}

