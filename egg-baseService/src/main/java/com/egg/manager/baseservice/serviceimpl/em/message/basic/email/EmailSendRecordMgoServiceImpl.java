package com.egg.manager.baseservice.serviceimpl.em.message.basic.email;

import com.egg.manager.api.services.em.message.basic.email.EmailSendRecordMgoService;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.persistence.em.message.db.mongo.repository.email.EmailSendRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Slf4j
@Service
public class EmailSendRecordMgoServiceImpl extends MyBaseMgoServiceImpl<EmailSendRecordRepository, EmailSendRecordMgo, Long>
        implements EmailSendRecordMgoService {

}
