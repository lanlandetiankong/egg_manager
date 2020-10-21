package com.egg.manager.persistence.db.mongo.repository.message.email;

import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Repository
@Component
public interface EmailSendRecordRepository extends MyBaseMongoRepository<EmailSendRecordMgo, String> {

}
