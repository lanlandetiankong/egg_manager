package com.egg.manager.api.services.serviceimpl.em.message.basic.email;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.facade.api.services.em.message.basic.email.EmailSendRecordMgoService;
import com.egg.manager.facade.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.facade.persistence.em.message.db.mongo.repository.email.EmailSendRecordRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = EmailSendRecordMgoService.class)
public class EmailSendRecordMgoServiceImpl extends MyBaseMgoServiceImpl<EmailSendRecordRepository, EmailSendRecordMgo, String>
        implements EmailSendRecordMgoService {

}
