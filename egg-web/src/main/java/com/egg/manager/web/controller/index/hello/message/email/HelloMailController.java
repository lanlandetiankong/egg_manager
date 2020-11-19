package com.egg.manager.web.controller.index.hello.message.email;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.message.basic.email.MyBaseEmailMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailReceiveUserInfoMgvo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@RestController
@RequestMapping("/index/hello/message/email/hellomail")
public class HelloMailController extends BaseController {
    @Value("${spring.mail.username}")
    private String fromUser;
    @Reference
    private MyBaseEmailMgoService myBaseEmailMgoService;

    @ApiOperation(value = "测试发送消息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/message/email/hello/sendMail")
    @PostMapping(value = "/sendMail")
    public WebResult doGetDefineModuleById(HttpServletRequest request) {
        WebResult result = WebResult.okOperation();
        String[] receiveEmails = new String[]{"2773756340@qq.com"};
        EmailSendRecordMgvo emailDto = EmailSendRecordMgvo.builder().subject("邮件标题123")
                .content("邮件内容123")
                .receiveUserInfoList(Lists.newArrayList(EmailReceiveUserInfoMgvo.builder().emailAddress("2773756340@qq.com").build()))
                .build();
        myBaseEmailMgoService.sendSimpleEmail(emailDto);
        return result;
    }
}
