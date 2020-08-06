package com.egg.manager.message.email.service;

import com.egg.manager.persistence.message.mail.MyEmailDto;

public interface MyBaseEmailService {

    /**
     * 发送普通文本
     * @param emailBean
     */
    void sendSimpleEmail(MyEmailDto emailBean) ;

    /**
     * 发送邮件(带附件)
     * @param emailBean
     */
    void sendAttachmentsMail(MyEmailDto emailBean);

    /**
     * 发送 freemarker 模板的邮件
     * @param emailDto
     * @throws Exception
     */
    void sendFreemarker(MyEmailDto emailDto) throws Exception;
}
