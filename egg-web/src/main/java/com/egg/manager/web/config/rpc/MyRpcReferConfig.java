package com.egg.manager.web.config.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


public class MyRpcReferConfig {

    @Configuration
    @ImportResource(locations = {
            "classpath:${egg.application.build.env}/dubbo/egg-web-dubboConfig.xml",
            "classpath*:universal/${egg.application.build.env}/dubbo/consumer/*/egg-universal-dubboConsumer-*.xml"
    })
    static class DubboReferConfig {

    }
}
