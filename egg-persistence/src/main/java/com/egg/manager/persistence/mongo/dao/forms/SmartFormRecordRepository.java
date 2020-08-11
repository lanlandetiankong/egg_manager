package com.egg.manager.persistence.mongo.dao.forms;

import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormRecordMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单记录-dao
 */
@Repository
@Component
public interface SmartFormRecordRepository extends MyBaseMongoRepository<SmartFormRecordMO, String> {


}
