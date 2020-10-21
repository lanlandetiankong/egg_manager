package com.egg.manager.persistence.db.mongo.repositoryimpl.message.email;

import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Repository
public class EmailSendRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<EmailSendRecordMgo, String>
        implements EmailSendRecordRepository {

}
