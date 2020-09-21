package com.egg.manager.persistence.db.mongo.repositoryImpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public class SmartFormFieldDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormFieldDefinitionMO, String>
        implements SmartFormFieldDefinitionRepository {


}
