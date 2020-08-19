package com.egg.manager.message.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.message.hello.MessageHelloService;
import com.egg.manager.api.service.service.hello.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service(interfaceClass = MessageHelloService.class)
public class MessageHelloServiceImpl implements MessageHelloService {
    @Value("${server.port}")
    private String serverPort ;
    @Value("${dubbo.application.name}")
    private String dubboApplicationName ;

    @Reference
    private HelloService helloService ;
    @Override
    public String loadBalancePort(){
        System.out.println("for message ->"+serverPort);
        return serverPort;
    }

    @Override
    public String loadServiceBalancePort(){
        String servicePort = helloService.loadBalanceTest();
        return servicePort;
    }
}