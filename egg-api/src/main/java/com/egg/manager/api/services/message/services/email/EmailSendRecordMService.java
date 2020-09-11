package com.egg.manager.api.services.message.services.email;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Repository
public interface EmailSendRecordMService extends MyBaseMongoService<EmailSendRecordMO,String> {


}
