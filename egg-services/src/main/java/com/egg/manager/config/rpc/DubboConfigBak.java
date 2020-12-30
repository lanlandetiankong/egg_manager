package com.egg.manager.config.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class DubboConfigBak {

    @Configuration
    @ImportResource(locations = {
           // "classpath:config/dubbo/egg-services-dubboConfig.xml"
    })
    static class DubboReferConfig {

    }
}
