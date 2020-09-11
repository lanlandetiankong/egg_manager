package com.egg.manager.persistence.db.mongo.repositoryImpl.message.email;

import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMO;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @ClassName: EmailSendRecordMO
 * @Author: zhoucj
 * @Date: 2020/9/11 14:55
 */
@Repository
public class EmailSendRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<EmailSendRecordMO, String>
        implements EmailSendRecordRepository {

}
