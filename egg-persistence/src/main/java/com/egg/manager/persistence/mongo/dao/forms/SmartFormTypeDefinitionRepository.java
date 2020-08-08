package com.egg.manager.persistence.mongo.dao.forms;

import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.stereotype.Component;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Component
public interface SmartFormTypeDefinitionRepository extends MyBaseMongoRepository<SmartFormTypeDefinitionMO, String> {

}
