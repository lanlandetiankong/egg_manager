package com.egg.manager.web.controller.index.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.hello.HelloService;
import com.egg.manager.api.services.message.services.hello.MessageHelloService;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonMenuTree;
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
 * @author: zhouchengjie
 * \* Date: 2020/7/18
 * \* Time: 14:19
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-测试接口")
@RestController
@RequestMapping("/index/hello")
public class HelloController extends BaseController {

    @Value("${spring.profiles.active}")
    private String avtiveEnv;
    @Reference
    private HelloService helloService;

    @Reference
    private MessageHelloService messageHelloService;
    private CommonMenuTree commonMenuTree ;

    @PcWebQueryLog(fullPath = "/index/hello/testEnv", flag = false)
    @ApiOperation(value = "测试当前开发环境",response = MyCommonResult.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/testEnv")
    public String doGetAllDefineDepartmentDtos() {
        try {
            return "You run in a " + avtiveEnv + " environment";
        } catch (Exception e) {
        }
        return "Error";
    }

    @PcWebQueryLog(fullPath = "/index/hello/sayHello", flag = false)
    @ApiOperation(value = "测试当前开发环境",response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/sayHello")
    public void sayHello() {
        helloService.sayHello();
    }

    @PcWebQueryLog(fullPath = "/index/hello/loadBalanceTest", flag = false)
    @ApiOperation(value = "测试dubbo负载均衡",response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/loadBalanceTest")
    public void loadBalanceTest() {
        String port = helloService.loadBalanceTest();
        log.info("for debug..." + port);
    }

    @PcWebQueryLog(fullPath = "/index/hello/loadMessageBalanceTest", flag = false)
    @ApiOperation(value = "测试dubbo负载均衡-相互调用",response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/loadMessageBalanceTest")
    public void loadMessageBalanceTest() {
        String port = messageHelloService.loadServiceBalancePort();
        log.info("for message  debug..." + port);
    }

}
