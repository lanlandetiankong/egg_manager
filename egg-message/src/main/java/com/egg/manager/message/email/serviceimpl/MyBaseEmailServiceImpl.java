package com.egg.manager.message.email.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.egg.manager.message.email.service.MyBaseEmailService;
import com.egg.manager.persistence.message.mail.MyEmailDto;
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
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class MyBaseEmailServiceImpl implements MyBaseEmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    public Configuration configuration;
    @Value("${spring.mail.username}")
    private String DEFAULT_SEND_USER_NAME;
    private String DEFAULT_SEND_PERSONAL_USER_NAME;


    @Override
    public void sendSimpleEmail(MyEmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage() ;
        mailMessage.setFrom(this.doGetMailFromUser(emailDto));
        mailMessage.setTo(emailDto.getReceiveEmails());
        mailMessage.setSubject(emailDto.getSubject());
        mailMessage.setText(emailDto.getContent());
        mailSender.send(mailMessage);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(), JSON.toJSONString(emailDto));
    }
    @Override
    public void sendAttachmentsMail(MyEmailDto emailDto) {
        MimeMessage message = mailSender.createMimeMessage();
        int fileSize = CollectionUtils.isNotEmpty(emailDto.getFileList()) ? emailDto.getFileList().size() : 0 ;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.doGetMailFromUser(emailDto));
            helper.setTo(emailDto.getReceiveEmails());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getContent());
            String fileName = null;
            for (File file : emailDto.getFileList()) {
                fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(),fileSize,JSON.toJSONString(emailDto));
    }

    @Override
    public void sendFreemarker(MyEmailDto emailDto) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //这里可以自定义发信名称比如：爪哇笔记
        helper.setFrom(doGetMailFromUser(emailDto),doGetMailPersonalFromUser(emailDto));
        helper.setTo(emailDto.getReceiveEmails());
        helper.setSubject(emailDto.getSubject());
        Map<String, String> kvMap = (emailDto.getKvMap() != null) ? emailDto.getKvMap() : new HashMap<>();
        Template template = configuration.getTemplate(emailDto.getTemplate());
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, kvMap);
        helper.setText(text, true);
        mailSender.send(message);
        emailDto.setContent(text);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(), JSON.toJSONString(emailDto));
    }


    /**
     * 取得发送人
     * @param emailDto
     * @return
     */
    private String doGetMailFromUser(MyEmailDto emailDto){
        return StringUtils.isNotBlank(emailDto.getFromUser()) ? emailDto.getFromUser() : DEFAULT_SEND_USER_NAME;
    }
    /**
     * 取得发送人(个性化)
     * @param emailDto
     * @return
     */
    private String doGetMailPersonalFromUser(MyEmailDto emailDto){
        return StringUtils.isNotBlank(emailDto.getFromUserPersonal()) ? emailDto.getFromUserPersonal() : DEFAULT_SEND_PERSONAL_USER_NAME;
    }
}
