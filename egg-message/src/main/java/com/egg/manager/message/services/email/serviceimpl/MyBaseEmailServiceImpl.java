package com.egg.manager.message.services.email.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.egg.manager.api.services.message.email.service.MyBaseEmailService;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.message.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailSendFileInfoMVO;
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
import java.util.HashMap;
import java.util.Map;


@Slf4j
//@Service(interfaceClass = MyBaseEmailService.class)
@Service
public class MyBaseEmailServiceImpl implements MyBaseEmailService {
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
    public void sendSimpleEmail(EmailSendRecordMVO emailSendRecordMVO) {
        SimpleMailMessage mailMessage = emailSendRecordMapstruct.emailSendRecordMVO_CopyTo_SimpleMailMessage(emailSendRecordMVO);
        mailMessage.setFrom(this.doGetMailFromUser(emailSendRecordMVO));
        javaMailSender.send(mailMessage);
        //TODO 保存记录到数据库
        /*EmailSendRecordMO emailSendRecordMO = new EmailSendRecordMO();
        emailSendRecordRepository.save(emailSendRecordMO);*/
        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailSendRecordMVO.doGainReceiveUserNameList(),emailSendRecordMVO.getSubject(), JSON.toJSONString(mailMessage));
    }
    @Override
    public void sendAttachmentsMail(EmailSendRecordMVO emailSendRecordMVO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        int fileSize = CollectionUtils.isNotEmpty(emailSendRecordMVO.getAccessoryInfoList()) ? emailSendRecordMVO.getAccessoryInfoList().size() : 0 ;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.doGetMailFromUser(emailSendRecordMVO));
            helper.setTo(emailSendRecordMVO.doGainReceiveUserNameList().stream().toArray(String[]::new));
            helper.setSubject(emailSendRecordMVO.getSubject());
            helper.setText(emailSendRecordMVO.getContent());
            String fileName = null;
            for (EmailSendFileInfoMVO sendFileInfoMVO : emailSendRecordMVO.getAccessoryInfoList()) {
                fileName = MimeUtility.encodeText(sendFileInfoMVO.getFileName(), "GB2312", "B");
                //TODO helper.addAttachment(fileName, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        //TODO 保存记录到数据库
        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}",emailSendRecordMVO.doGainReceiveUserNameList(),emailSendRecordMVO.getSubject(),fileSize,JSON.toJSONString(emailSendRecordMVO));
    }

    @Override
    public void sendFreemarker(EmailSendRecordMVO emailSendRecordMVO) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //这里可以自定义发信名称比如：爪哇笔记
        helper.setFrom(doGetMailFromUser(emailSendRecordMVO),doGetMailPersonalFromUser(emailSendRecordMVO));
        helper.setTo(emailSendRecordMVO.getReceiveUserInfoList().stream().toArray(String[]::new));
        helper.setSubject(emailSendRecordMVO.getSubject());
        Map<String, String> kvMap = (emailSendRecordMVO.getKvMap() != null) ? emailSendRecordMVO.getKvMap() : new HashMap<>();
        Template template = configuration.getTemplate(emailSendRecordMVO.getTemplate());
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, kvMap);
        helper.setText(text, true);
        javaMailSender.send(message);
        emailSendRecordMVO.setContent(text);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailSendRecordMVO.doGainReceiveUserNameList(),emailSendRecordMVO.getSubject(), JSON.toJSONString(emailSendRecordMVO));
    }


    /**
     * 取得发送人
     * @param emailSendRecordMVO
     * @return
     */
    private String doGetMailFromUser(EmailSendRecordMVO emailSendRecordMVO){
        if(emailSendRecordMVO.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMVO.getFromUserInfo().getEmailAddress())){
            return DEFAULT_SEND_USER_NAME ;
        }
        return emailSendRecordMVO.getFromUserInfo().getEmailAddress();
    }
    /**
     * 取得发送人(个性化)
     * @param emailSendRecordMVO
     * @return
     */
    private String doGetMailPersonalFromUser(EmailSendRecordMVO emailSendRecordMVO){
        if(emailSendRecordMVO.getFromUserInfo() == null || StringUtils.isBlank(emailSendRecordMVO.getFromUserInfo().getPersonal())){
            return DEFAULT_SEND_PERSONAL_USER_NAME ;
        }
        return emailSendRecordMVO.getFromUserInfo().getPersonal() ;
    }
}
