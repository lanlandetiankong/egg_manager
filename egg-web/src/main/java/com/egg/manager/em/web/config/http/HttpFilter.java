package com.egg.manager.em.web.config.http;

import com.egg.manager.persistence.commons.util.basic.http.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;

/**
 * 跨域请求过滤器
 * @author user
 * @date 2019/4/29
 */
@Configuration
public class HttpFilter {
    /**
     * 跨域请求过滤器
     * @return
     */
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

    /**
     * xss过滤拦截器
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
