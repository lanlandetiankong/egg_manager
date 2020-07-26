package com.egg.manager.persistence.mongo.dao.forms;

import com.egg.manager.persistence.mongo.mo.forms.SmartFormDefinitionMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public interface SmartFormDefinitionRepository extends MongoRepository<SmartFormDefinitionMO, String> {


}
