package com.egg.manager.api.services.message.email.service;

import com.egg.manager.persistence.pojo.common.message.mail.MyEmailMsgO;

public interface MyBaseEmailService {

    /**
     * 发送普通文本
     * @param emailBean
     */
    void sendSimpleEmail(MyEmailMsgO emailBean) ;

    /**
     * 发送邮件(带附件)
     * @param emailBean
     */
    void sendAttachmentsMail(MyEmailMsgO emailBean);

    /**
     * 发送 freemarker 模板的邮件
     * @param emailDto
     * @throws Exception
     */
    void sendFreemarker(MyEmailMsgO emailDto) throws Exception;
}
