package com.egg.manager.service.config.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


public class MyRpcReferConfig {

    @Configuration
    @ImportResource(locations = {
            "classpath:${egg.application.build.env}/dubbo/egg-baseService-dubboConfig.xml",
            "classpath:${egg.application.build.env}/dubbo/provider/egg-baseService-dubboProvider-*.xml",
            "classpath*:universal/${egg.application.build.env}/dubbo/consumer/*/egg-universal-dubboConsumer-*.xml"
    })
    static class DubboReferConfig {

    }
}
