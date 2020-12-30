package com.egg.manager.config.rpc;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Configuration
@DubboComponentScan(basePackages = "com.egg.manager.api.services")
public class DubboConfig {

}
