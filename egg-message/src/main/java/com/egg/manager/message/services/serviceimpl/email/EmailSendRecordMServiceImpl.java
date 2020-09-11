package com.egg.manager.message.services.serviceimpl.email;

import com.egg.manager.api.services.message.services.email.EmailSendRecordMService;
import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMO;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class EmailSendRecordMServiceImpl extends MyBaseMongoServiceImpl<EmailSendRecordRepository, EmailSendRecordMO,String>
        implements EmailSendRecordMService {

}
