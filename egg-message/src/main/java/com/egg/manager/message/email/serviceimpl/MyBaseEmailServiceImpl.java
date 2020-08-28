package com.egg.manager.message.email.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.egg.manager.api.services.message.email.service.MyBaseEmailService;
import com.egg.manager.persistence.pojo.message.mail.MyEmailMsgO;
import com.egg.manager.persistence.pojo.message.mapstruct.mail.MessageMailMapstruct;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service(interfaceClass = MyBaseEmailService.class)
public class MyBaseEmailServiceImpl implements MyBaseEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    public Configuration configuration;
    @Value("${spring.mail.username}")
    private String DEFAULT_SEND_USER_NAME;
    @Value("${spring.application.name}")
    private String DEFAULT_SEND_PERSONAL_USER_NAME;

    @Autowired
    private MessageMailMapstruct messageMailMapstruct;


    @Override
    public void sendSimpleEmail(MyEmailMsgO emailDto) {
        SimpleMailMessage mailMessage = messageMailMapstruct.myEmailMsgO_CopyTo_SimpleMailMessage(emailDto);
        mailMessage.setFrom(this.doGetMailFromUser(emailDto));
        javaMailSender.send(mailMessage);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(), JSON.toJSONString(mailMessage));
    }
    @Override
    public void sendAttachmentsMail(MyEmailMsgO emailDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
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
        javaMailSender.send(message);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(),fileSize,JSON.toJSONString(emailDto));
    }

    @Override
    public void sendFreemarker(MyEmailMsgO emailDto) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //这里可以自定义发信名称比如：爪哇笔记
        helper.setFrom(doGetMailFromUser(emailDto),doGetMailPersonalFromUser(emailDto));
        helper.setTo(emailDto.getReceiveEmails());
        helper.setSubject(emailDto.getSubject());
        Map<String, String> kvMap = (emailDto.getKvMap() != null) ? emailDto.getKvMap() : new HashMap<>();
        Template template = configuration.getTemplate(emailDto.getTemplate());
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, kvMap);
        helper.setText(text, true);
        javaMailSender.send(message);
        emailDto.setContent(text);
        //TODO 保存记录到数据库

        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(), JSON.toJSONString(emailDto));
    }


    /**
     * 取得发送人
     * @param emailDto
     * @return
     */
    private String doGetMailFromUser(MyEmailMsgO emailDto){
        return StringUtils.isNotBlank(emailDto.getFromUser()) ? emailDto.getFromUser() : DEFAULT_SEND_USER_NAME;
    }
    /**
     * 取得发送人(个性化)
     * @param emailDto
     * @return
     */
    private String doGetMailPersonalFromUser(MyEmailMsgO emailDto){
        return StringUtils.isNotBlank(emailDto.getFromUserPersonal()) ? emailDto.getFromUserPersonal() : DEFAULT_SEND_PERSONAL_USER_NAME;
    }
}
