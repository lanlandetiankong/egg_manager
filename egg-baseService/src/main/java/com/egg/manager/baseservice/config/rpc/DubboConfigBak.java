package com.egg.manager.baseservice.config.rpc;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
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
           // "classpath:config/dubbo/egg-baseService-dubboConfig.xml"
    })
    static class DubboReferConfig {

    }
}
