package com.egg.manager.persistence.db.mongo.dao.forms;

import com.egg.manager.persistence.db.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public interface SmartFormDefinitionRepository extends MyBaseMongoRepository<SmartFormDefinitionMO, String> {


}
