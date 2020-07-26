package com.egg.manager.persistence.mongo.dao.forms;

import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Repository
@Component
public interface SmartFormTypeDefinitionRepository extends MongoRepository<SmartFormTypeDefinitionMO, String> {

}
