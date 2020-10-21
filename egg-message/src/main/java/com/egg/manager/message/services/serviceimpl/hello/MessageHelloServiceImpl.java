package com.egg.manager.message.services.serviceimpl.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.hello.HelloService;
import com.egg.manager.api.services.message.services.hello.MessageHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = MessageHelloService.class)
public class MessageHelloServiceImpl implements MessageHelloService {
    @Value("${server.port}")
    private String serverPort;
    @Value("${egg.dubbo.application.name}")
    private String dubboApplicationName;
    @Reference
    private HelloService helloService;

    @Override
    public String loadBalancePort() {
        log.info("for message ->" + serverPort);
        return serverPort;
    }

    @Override
    public String loadServiceBalancePort() {
        String servicePort = helloService.loadBalanceTest();
        return servicePort;
    }
}
