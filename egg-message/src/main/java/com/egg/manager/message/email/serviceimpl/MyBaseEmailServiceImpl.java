package com.egg.manager.message.email.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.egg.manager.message.email.service.MyBaseEmailService;
import com.egg.manager.persistence.message.mail.MyEmailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;


@Slf4j
@Service
public class MyBaseEmailServiceImpl implements MyBaseEmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    public String DEFAULT_SEND_USER_NAME;


    @Override
    public void sendSimpleEmail(MyEmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage() ;
        mailMessage.setFrom(this.getFromUser(emailDto));
        mailMessage.setTo(emailDto.getReceiveEmails());
        mailMessage.setSubject(emailDto.getSubject());
        mailMessage.setText(emailDto.getContent());
        mailSender.send(mailMessage);
        log.info("发送给{}的标题为< {} >邮件已经发送。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(), JSON.toJSONString(emailDto));
    }
    @Override
    public void sendAttachmentsMail(MyEmailDto emailDto) {
        MimeMessage message = mailSender.createMimeMessage();
        int fileSize = CollectionUtils.isNotEmpty(emailDto.getFileList()) ? emailDto.getFileList().size() : 0 ;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.getFromUser(emailDto));
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
        log.info("发送给{}的标题为< {} >邮件已经发送(含附件共{}个)。参数JSON为{}",emailDto.getReceiveEmails(),emailDto.getSubject(),fileSize,JSON.toJSONString(emailDto));
    }


    /**
     * 取得发送人
     * @param emailDto
     * @return
     */
    private String getFromUser(MyEmailDto emailDto){
        return StringUtils.isNotBlank(emailDto.getFromUser()) ? emailDto.getFromUser() : DEFAULT_SEND_USER_NAME;
    }
}
