package com.egg.manager.message.config.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public class MyRpcReferConfig {


    @Configuration
    @ImportResource(locations = {
            "classpath:${egg.application.build.env}/dubbo/egg-message-dubboConfig.xml",
            "classpath:${egg.application.build.env}/dubbo/provider/egg-message-dubboProvider-*.xml",
            "classpath*:universal/${egg.application.build.env}/dubbo/consumer/*/egg-universal-dubboConsumer-*.xml"
    })
    static class DubboReferConfig {

    }

}
