package com.egg.manager.service.serviceimpl.hello;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.message.email.service.MyBaseEmailService;
import com.egg.manager.api.service.service.hello.HelloService;
import com.egg.manager.api.service.service.user.UserAccountService;
import com.egg.manager.persistence.message.mail.MyEmailMsgO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service(interfaceClass = HelloService.class)
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Reference
    private UserAccountService userAccountService ;
    @Reference
    private MyBaseEmailService myBaseEmailService ;

    @Value("${server.port}")
    private String serverPort ;
    @Value("${dubbo.application.name}")
    private String dubboApplicationName ;

    @Autowired
    private RegistryConfig registryConfig;



    @Override
    public void sayHello() {
        //userAccountService.selectPage(null);

        myBaseEmailService.sendSimpleEmail(MyEmailMsgO.builder().content("测试").build());

    }

    @Override
    public String loadBalanceTest() {
        System.out.println("for service ->"+serverPort);
        return serverPort;
    }



}
