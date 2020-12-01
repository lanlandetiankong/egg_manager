package com.egg.manager.em.config.http;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跨域请求过滤器
 * @author user
 * @date 2019/4/29
 */
@Configuration
public class OriginFilter {
    @SuppressWarnings("unchecked")
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        //必要,设置可返回给前端取得的自定义header
        this.dealAddExposedHeaders(corsConfiguration, "authorization", "Content-Disposition");
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


    public void dealAddExposedHeaders(CorsConfiguration corsConfiguration, String... exposedHeaderArr) {
        List<String> exposedHeaderList = corsConfiguration.getExposedHeaders();
        exposedHeaderList = exposedHeaderList != null ? exposedHeaderList : new ArrayList<>();
        if (exposedHeaderArr != null && exposedHeaderArr.length > 0) {
            for (String exposedItem : exposedHeaderArr) {
                exposedHeaderList.add(exposedItem);
            }
        }
        corsConfiguration.setExposedHeaders(exposedHeaderList);
    }

}
