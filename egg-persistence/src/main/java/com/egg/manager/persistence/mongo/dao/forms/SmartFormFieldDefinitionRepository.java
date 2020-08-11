package com.egg.manager.persistence.mongo.dao.forms;

import com.egg.manager.persistence.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormFieldDefinitionMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public interface SmartFormFieldDefinitionRepository extends MyBaseMongoRepository<SmartFormFieldDefinitionMO, String> {


}
