package com.egg.manager.persistence.db.mongo.repositoryImpl.forms;

import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormRecordRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单记录-dao
 */
@Repository
@Component
public class SmartFormRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormRecordMO, String>
        implements SmartFormRecordRepository {


}
