package com.egg.manager.api.services.message.services.email;

import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMgvo;

public interface MyBaseEmailMgoService {

    /**
     * 发送普通文本
     * @param emailSendRecordMgvo
     */
    void sendSimpleEmail(EmailSendRecordMgvo emailSendRecordMgvo) ;

    /**
     * 发送邮件(带附件)
     * @param emailSendRecordMgvo
     */
    void sendAttachmentsMail(EmailSendRecordMgvo emailSendRecordMgvo);

    /**
     * 发送 freemarker 模板的邮件
     * @param emailSendRecordMgvo
     * @throws Exception
     */
    void sendFreemarker(EmailSendRecordMgvo emailSendRecordMgvo) throws Exception;
}