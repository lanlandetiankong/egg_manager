package com.egg.manager.persistence.db.mongo.repository.forms;

import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.stereotype.Component;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Component
public interface SmartFormTypeDefinitionRepository extends MyBaseMongoRepository<SmartFormTypeDefinitionMO, String> {

}
