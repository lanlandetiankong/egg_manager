package com.egg.manager.message.services.serviceimpl.email;

import com.egg.manager.api.services.message.services.email.EmailSendRecordMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class EmailSendRecordMgoServiceImpl extends MyBaseMgoServiceImpl<EmailSendRecordRepository, EmailSendRecordMgo,String>
        implements EmailSendRecordMgoService {

}
