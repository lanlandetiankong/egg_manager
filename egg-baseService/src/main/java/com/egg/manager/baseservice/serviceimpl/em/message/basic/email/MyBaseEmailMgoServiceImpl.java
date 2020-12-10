package com.egg.manager.baseservice.serviceimpl.em.message.basic.email;

import com.alibaba.fastjson.JSON;
import com.egg.manager.api.services.em.message.basic.email.MyBaseEmailMgoService;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.em.message.db.mongo.repository.email.EmailSendRecordRepository;
import com.egg.manager.persistence.em.message.pojo.mapstruct.imap.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailSendFileInfoMgvo;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Service
public class MyBaseEmailMgoServiceImpl implements MyBaseEmailMgoService {
    @Autowired
    private EmailSendRecordMapstruct emailSendRecordMapstruct;


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    public Configuration configuration;
    @Value("${spring.mail.username}")
    private String defaultSendUserName;
    @Value("${spring.application.name}")
    private String defaultSendPersonalUserName;


    @Autowired
    private EmailSendRecordRepository emailSendRecordRepository;

    @Override
    public void sendSimpleEmail(EmailSendRecordMgvo emailSendRecordMgvo) {
        SimpleMailMessage mailMessage = emailSendRecordMapstruct.emailSendRecordMVO_CopyTo_SimpleMailMessage(emailSendRecordMgvo);
        mailMessage.setFrom(this.doGetMailFromUser(emailSendRecordMgvo));
        javaMailSender.send(mailMessage);
        //TODO 保存记录到数据库
        /*EmailSendRecordMgo emailSendRecordMO = new EmailSendRecordMgo();
        emailSendRecordRepository.save(emailSendRecordMO);*/
        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}", emailSendRecordMgvo.doGainReceiveUserNameList(), emailSendRecordMgvo.getSubject(), JSON.toJSONString(mailMessage));
    }

    @Override
    public void sendAttachmentsMail(EmailSendRecordMgvo emailSendRecordMgvo) {
        MimeMessage message = javaMailSender.createMimeMessage();
        int fileSize = CollectionUtils.isNotEmpty(emailSendRecordMgvo.getAccessoryInfoList()) ? emailSendRecordMgvo.getAccessoryInfoList().size() : 0;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.doGetMailFromUser(emailSendRecordMgvo));
            helper.setTo(emailSendRecordMgvo.doGainReceiveUserNameList().stream().toArray(String[]::new));
            helper.setSubject(emailSendRecordMgvo.getSubject());
            helper.setText(emailSendRecordMgvo.getContent());
            String fileName = null;
            for (EmailSendFileInfoMgvo sendFileInfoMgvo : emailSendRecordMgvo.getAccessoryInfoList()) {
                fileName = MimeUtility.encodeText(sendFileInfoMgvo.getFileName(), "GB2312", "B");
            }
        } catch (Exception e) {
            log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"),e);
        }
        javaMailSender.send(message);
        //TODO 保存记录到数据库
        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}", emailSendRecordMgvo.doGainReceiveUserNameList(), emailSendRecordMgvo.getSubject(), fileSize, JSON.toJSONString(emailSendRecordMgvo));
    }

    @Override
    public void sendFreemarker(EmailSendRecordMgvo emailSendRecordMgvo) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //这里可以自定义发信名称比如：爪哇笔记
        helper.setFrom(doGetMailFromUser(emailSendRecordMgvo), doGetMailPersonalFromUser(emailSendRecordMgvo));
        helper.setTo(emailSendRecordMgvo.getReceiveUserInfoList().stream().toArray(String[]::new));
        helper.setSubject(emailSendRecordMgvo.getSubject());
        Map<String, String> kvMap = (emailSendRecordMgvo.getKvMap() != null) ? emailSendRecordMgvo.getKvMap() : Maps.newHashMap();
        Template template = configuration.getTemplate(emailSendRecordMgvo.getTemplate());
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, kvMap);
        helper.setText(text, true);
        javaMailSender.send(message);
        emailSendRecordMgvo.setContent(text);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}", emailSendRecordMgvo.doGainReceiveUserNameList(), emailSendRecordMgvo.getSubject(), JSON.toJSONString(emailSendRecordMgvo));
    }


    /**
     * 取得发送人
     * @param emailSendRecordMgvo
     * @return
     */
    private String doGetMailFromUser(EmailSendRecordMgvo emailSendRecordMgvo) {
        if (emailSendRecordMgvo.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMgvo.getFromUserInfo().getEmailAddress())) {
            return defaultSendPersonalUserName;
        }
        return emailSendRecordMgvo.getFromUserInfo().getEmailAddress();
    }

    /**
     * 取得发送人(个性化)
     * @param emailSendRecordMgvo
     * @return
     */
    private String doGetMailPersonalFromUser(EmailSendRecordMgvo emailSendRecordMgvo) {
        if (emailSendRecordMgvo.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMgvo.getFromUserInfo().getPersonal())) {
            return defaultSendPersonalUserName;
        }
        return emailSendRecordMgvo.getFromUserInfo().getPersonal();
    }
}
