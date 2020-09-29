package com.egg.manager.persistence.db.mongo.repository.message.email;

import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @ClassName: EmailSendRecordRepository
 * @Author: zhoucj
 * @Date: 2020/9/11 14:55
 */
@Repository
@Component
public interface EmailSendRecordRepository extends MyBaseMongoRepository<EmailSendRecordMgo, String> {

}
