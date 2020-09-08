package com.egg.manager.persistence.db.mongo.repository.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单记录-dao
 */
@Repository
@Component
public interface SmartFormRecordRepository extends MyBaseMongoRepository<SmartFormRecordMO, String> {


}
