package com.egg.manager.message.services.serviceimpl.email;

import com.alibaba.fastjson.JSON;
import com.egg.manager.api.services.message.services.email.MyBaseEmailMgoService;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.message.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailSendFileInfoMgvo;
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
    private String DEFAULT_SEND_USER_NAME;
    @Value("${spring.application.name}")
    private String DEFAULT_SEND_PERSONAL_USER_NAME;



    @Autowired
    private EmailSendRecordRepository emailSendRecordRepository ;

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
        int fileSize = CollectionUtils.isNotEmpty(emailSendRecordMgvo.getAccessoryInfoList()) ? emailSendRecordMgvo.getAccessoryInfoList().size() : 0 ;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.doGetMailFromUser(emailSendRecordMgvo));
            helper.setTo(emailSendRecordMgvo.doGainReceiveUserNameList().stream().toArray(String[]::new));
            helper.setSubject(emailSendRecordMgvo.getSubject());
            helper.setText(emailSendRecordMgvo.getContent());
            String fileName = null;
            for (EmailSendFileInfoMgvo sendFileInfoMVO : emailSendRecordMgvo.getAccessoryInfoList()) {
                fileName = MimeUtility.encodeText(sendFileInfoMVO.getFileName(), "GB2312", "B");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        //TODO 保存记录到数据库
        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}", emailSendRecordMgvo.doGainReceiveUserNameList(), emailSendRecordMgvo.getSubject(),fileSize,JSON.toJSONString(emailSendRecordMgvo));
    }

    @Override
    public void sendFreemarker(EmailSendRecordMgvo emailSendRecordMgvo) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //这里可以自定义发信名称比如：爪哇笔记
        helper.setFrom(doGetMailFromUser(emailSendRecordMgvo),doGetMailPersonalFromUser(emailSendRecordMgvo));
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
    private String doGetMailFromUser(EmailSendRecordMgvo emailSendRecordMgvo){
        if(emailSendRecordMgvo.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMgvo.getFromUserInfo().getEmailAddress())){
            return DEFAULT_SEND_USER_NAME ;
        }
        return emailSendRecordMgvo.getFromUserInfo().getEmailAddress();
    }
    /**
     * 取得发送人(个性化)
     * @param emailSendRecordMgvo
     * @return
     */
    private String doGetMailPersonalFromUser(EmailSendRecordMgvo emailSendRecordMgvo){
        if(emailSendRecordMgvo.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMgvo.getFromUserInfo().getPersonal())){
            return DEFAULT_SEND_PERSONAL_USER_NAME ;
        }
        return emailSendRecordMgvo.getFromUserInfo().getPersonal() ;
    }
}
