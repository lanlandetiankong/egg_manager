package com.egg.manager.web.controller.index.hello.message.email;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.message.services.email.MyBaseEmailMgoService;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailReceiveUserInfoMgvo;
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
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/8/6
 * \* Time: 22:30
 * \* Description:
 * \
 */
@Slf4j
@RestController
@RequestMapping("/index/hello/message/email/hellomail")
public class HelloMailController extends BaseController {
    @Value("${spring.mail.username}")
    private String fromUser;
    @Reference
    private MyBaseEmailMgoService myBaseEmailMgoService;

    @ApiOperation(value = "测试发送消息", notes = "测试发送消息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "测试发送消息", description = "测试发送消息", fullPath = "/message/email/hello/sendMail")
    @PostMapping(value = "/sendMail")
    public MyCommonResult doGetDefineModuleById(HttpServletRequest request) {
        MyCommonResult result = MyCommonResult.gainOperationResult("测试");
        try {
            String[] receiveEmails = new String[]{"2773756340@qq.com"};
            EmailSendRecordMgvo emailDto = EmailSendRecordMgvo.builder().subject("邮件标题123")
                    .content("邮件内容123")
                    .receiveUserInfoList(Lists.newArrayList(EmailReceiveUserInfoMgvo.builder().emailAddress("2773756340@qq.com").build()))
                    .build();
            myBaseEmailMgoService.sendSimpleEmail(emailDto);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,"测试");
        }
        return result;
    }
}