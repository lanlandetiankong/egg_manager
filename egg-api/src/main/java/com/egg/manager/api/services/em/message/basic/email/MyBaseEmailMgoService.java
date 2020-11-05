package com.egg.manager.api.services.em.message.basic.email;

import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface MyBaseEmailMgoService {

    /**
     * 发送普通文本
     * @param emailSendRecordMgvo
     */
    void sendSimpleEmail(EmailSendRecordMgvo emailSendRecordMgvo);

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
