package com.egg.manager.persistence.db.mongo.repository.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Component
public interface SmartFormTypeDefinitionRepository extends MyBaseMongoRepository<SmartFormTypeDefinitionMgo, String> {

}
