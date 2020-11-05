package com.egg.manager.persistence.em.message.db.mongo.repositoryimpl.email;

import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.persistence.em.message.db.mongo.repository.email.EmailSendRecordRepository;
import com.egg.manager.persistence.enhance.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Repository
public class EmailSendRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<EmailSendRecordMgo, Long>
        implements EmailSendRecordRepository {

}
