package com.egg.manager.baseService.services.basic.serviceimpl.hello;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.message.email.service.MyBaseEmailService;
import com.egg.manager.api.services.basic.hello.HelloService;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.persistence.pojo.message.mail.MyEmailMsgO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service(interfaceClass = HelloService.class)
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Autowired
    private UserAccountService userAccountService ;
    @Reference
    private MyBaseEmailService myBaseEmailService ;

    @Value("${server.port}")
    private String serverPort ;
    @Value("${egg.dubbo.application.name}")
    private String dubboApplicationName ;

    @Autowired
    private RegistryConfig registryConfig;



    @Override
    public String sayHello() {
        //userAccountService.selectPage(null);

        myBaseEmailService.sendSimpleEmail(MyEmailMsgO.builder().content("测试").build());
        return "ok";
    }

    @Override
    public String loadBalanceTest() {
        System.out.println("for service ->"+serverPort);
        return serverPort;
    }



}
