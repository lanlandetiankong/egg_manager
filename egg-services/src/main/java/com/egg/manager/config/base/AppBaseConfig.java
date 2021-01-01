package com.egg.manager.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @description: 自定义导入spring配置文件
 * @author zhoucj
 * @date 2020/12/31
 */
public class AppBaseConfig {

    /**
     * 支持xml导入全局配置文件
     */
    @Configuration
    @ImportResource(locations = {
            //"classpath*:${egg.application.build.env}/dubbo/egg-message-dubboConfig.xml",
    })
    static class BaseXmlConfig {

    }

    /**
     * 支持properties导入全局配置文件
     * Application的更多配置引申(由spring.profiles.active指定改为当前配置)
     * @note 只可使用classpath，不可使用classpath*，classpath*会导致找不到文件
     *
     */
    @Configuration
    @PropertySource(value = {
            "classpath:config/application-${egg.application.build.env}-egg.properties",
            "classpath:config/application-${egg.application.build.env}-universal.properties",
    },encoding="UTF-8")
    static class BasePropConfig {

    }



}