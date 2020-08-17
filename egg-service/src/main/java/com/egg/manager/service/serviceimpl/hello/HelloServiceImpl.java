package com.egg.manager.service.serviceimpl.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.message.email.service.MyBaseEmailService;
import com.egg.manager.api.service.service.hello.HelloService;
import com.egg.manager.api.service.service.user.UserAccountService;
import com.egg.manager.persistence.message.mail.MyEmailMsgO;

@Service(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Reference
    private UserAccountService userAccountService ;
    @Reference
    private MyBaseEmailService myBaseEmailService ;



    @Override
    public void sayHello() {
        //userAccountService.selectPage(null);

        myBaseEmailService.sendSimpleEmail(MyEmailMsgO.builder().content("测试").build());

    }
}
