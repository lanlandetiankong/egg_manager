package com.egg.manager.web.controller.message.email;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.message.services.email.MyBaseEmailService;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailReceiveUserInfoMVO;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
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
@RequestMapping("/message/email/hello")
public class HelloMailController extends BaseController{
    @Value("${spring.mail.username}")
    private String fromUser ;
    @Reference
    private MyBaseEmailService myBaseEmailService ;

    @ApiOperation(value = "测试发送消息", notes = "测试发送消息", response = MyCommonResult.class,httpMethod = "POST")
    @PcWebQueryLog(action="测试发送消息",description = "测试发送消息",fullPath = "/message/email/hello/sendMail")
    @PostMapping(value = "/sendMail")
    public MyCommonResult<DefineModuleVo> doGetDefineModuleById(HttpServletRequest request) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            String[] receiveEmails = new String[]{"2773756340@qq.com"} ;
            EmailSendRecordMVO emailDto = EmailSendRecordMVO.builder().subject("邮件标题123")
                    .content("邮件内容123")
                    .receiveUserInfoList(Lists.newArrayList(EmailReceiveUserInfoMVO.builder().emailAddress("2773756340@qq.com").build()))
                    .build();
            myBaseEmailService.sendSimpleEmail(emailDto);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }
}
