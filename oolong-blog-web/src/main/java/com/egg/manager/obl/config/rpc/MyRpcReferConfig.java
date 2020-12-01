package com.egg.manager.obl.config.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class MyRpcReferConfig {

    @Configuration
    @ImportResource(locations = {
            "classpath:config/dubbo/oolong-blog-web-dubboConfig.xml",
            //"classpath*:config/dubbo/beans/*/oolong-blog-web-dubboBean-*.xml",
            "classpath*:universal/${egg.application.build.env}/dubbo/consumer/*/egg-universal-dubboConsumer-*.xml"
    })
    static class DubboReferConfig {

    }
}
