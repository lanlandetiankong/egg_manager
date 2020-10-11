package com.egg.manager.persistence.db.mongo.repositoryimpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public class SmartFormFieldDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormFieldDefinitionMgo, String>
        implements SmartFormFieldDefinitionRepository {


}