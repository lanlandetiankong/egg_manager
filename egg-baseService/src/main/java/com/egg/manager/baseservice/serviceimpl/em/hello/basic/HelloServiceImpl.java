package com.egg.manager.baseservice.serviceimpl.em.hello.basic;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.em.hello.basic.HelloService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.services.em.message.basic.email.MyBaseEmailMgoService;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Autowired
    private UserAccountService userAccountService;
    @Reference
    private MyBaseEmailMgoService myBaseEmailMgoService;

    @Value("${server.port}")
    private String serverPort;
    @Value("${egg.dubbo.application.name}")
    private String dubboApplicationName;

    @Autowired
    private RegistryConfig registryConfig;


    @Override
    public String sayHello() {
        myBaseEmailMgoService.sendSimpleEmail(EmailSendRecordMgvo.builder().content("测试").build());
        return "ok";
    }

    @Override
    public String loadBalanceTest() {
        log.info("for service ->" + serverPort);
        return serverPort;
    }


}
