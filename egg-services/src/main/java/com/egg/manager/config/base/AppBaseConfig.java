package com.egg.manager.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @description: 自定义导入spring配置文件
 * @author zhoucj
 * @date 2020/12/31
 */
public class AppBaseConfig {

    @Configuration
    @ImportResource(locations = {
            //"classpath*:${egg.application.build.env}/dubbo/egg-message-dubboConfig.xml",
    })
    static class BaseXmlConfig {

    }
}