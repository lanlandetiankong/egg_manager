package com.egg.manager.api.services.message.services.email;

import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMVO;

public interface MyBaseEmailService {

    /**
     * 发送普通文本
     * @param emailSendRecordMVO
     */
    void sendSimpleEmail(EmailSendRecordMVO emailSendRecordMVO) ;

    /**
     * 发送邮件(带附件)
     * @param emailSendRecordMVO
     */
    void sendAttachmentsMail(EmailSendRecordMVO emailSendRecordMVO);

    /**
     * 发送 freemarker 模板的邮件
     * @param emailSendRecordMVO
     * @throws Exception
     */
    void sendFreemarker(EmailSendRecordMVO emailSendRecordMVO) throws Exception;
}
