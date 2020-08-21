package com.egg.manager.web.controller.index;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.message.hello.MessageHelloService;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.service.service.hello.HelloService;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/18
 * \* Time: 14:19
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  HelloController ", description = "Index")
@RestController
@RequestMapping("/index/hello")
public class HelloController extends BaseController {

    @Value("${spring.profiles.active}")
    private String avtiveEnv ;
    @Reference
    private HelloService helloService ;

    @Reference
    private MessageHelloService messageHelloService ;

    @OperLog( action = "测试当前开发环境", description = "测试当前开发环境")
    @ApiOperation(value = "测试当前开发环境", notes = "测试当前开发环境", response = MyCommonResult.class, httpMethod = "POST")
    @GetMapping(value = "/testEnv")
    public String doGetAllDefineDepartmentDtos() {
        try {
            return "You run in a "+avtiveEnv+" environment";
        } catch (Exception e) {
        }
        return "Error";
    }


    @GetMapping(value = "/sayHello")
    public void sayHello(){
        helloService.sayHello();
    }

    @GetMapping(value = "/loadBalanceTest")
    public void loadBalanceTest(){
        String port = helloService.loadBalanceTest();
        System.out.println("for debug..."+port);
    }

    @GetMapping(value = "/loadMessageBalanceTest")
    public void loadMessageBalanceTest(){
        String port = messageHelloService.loadServiceBalancePort();
        System.out.println("for message  debug..."+port);
    }
}
