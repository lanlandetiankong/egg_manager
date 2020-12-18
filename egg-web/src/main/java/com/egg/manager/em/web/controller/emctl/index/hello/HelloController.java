package com.egg.manager.em.web.controller.emctl.index.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.util.i18n.I18nUtil;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.hello.basic.HelloService;
import com.egg.manager.api.services.em.hello.basic.MessageHelloService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-测试接口")
@RestController
@RequestMapping("/emCtl/index/hello")
public class HelloController extends BaseController {
    @Value("${spring.profiles.active}")
    private String avtiveEnv;
    @Reference
    private HelloService helloService;
    @Reference
    private MessageHelloService messageHelloService;
    private CommonMenuTree commonMenuTree;

    @EmPcWebQueryLog(fullPath = "/emCtl/index/hello/testEnv", flag = false)
    @ApiOperation(value = "测试当前开发环境", response = WebResult.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/testEnv")
    public String doGetAllDefineDepartmentDtos() {
        return "You run in a " + avtiveEnv + " environment";
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/index/hello/sayHello", flag = false)
    @ApiOperation(value = "测试当前开发环境", response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/sayHello")
    public void sayHello() {
        helloService.sayHello();
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/index/hello/loadBalanceTest", flag = false)
    @ApiOperation(value = "测试dubbo负载均衡", response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/loadBalanceTest")
    public void loadBalanceTest() {
        String port = helloService.loadBalanceTest();
        log.info("for debug..." + port);
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/index/hello/loadMessageBalanceTest", flag = false)
    @ApiOperation(value = "测试dubbo负载均衡-相互调用", response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/loadMessageBalanceTest")
    public void loadMessageBalanceTest() {
        String port = messageHelloService.loadServiceBalancePort();
        log.info("for message  debug..." + port);
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/index/hello/i18n", flag = false)
    @ApiOperation(value = "i18n", response = Void.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/i18n")
    public void i18n() {
        String s1 = BaseRstMsgConstant.ErrorMsg.formIncorrectParam();
        String s = I18nUtil.get("index.welcome");
        String s2 = I18nUtil.get("login.username");

        log.info("for message  debug..." + s);
        log.info("for message  debug..." + s2);
    }
}
