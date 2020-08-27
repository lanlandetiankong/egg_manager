package com.egg.manager.persistence.db.mongo.dao.forms;

import com.egg.manager.persistence.db.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单记录-dao
 */
@Repository
@Component
public interface SmartFormRecordRepository extends MyBaseMongoRepository<SmartFormRecordMO, String> {


}
