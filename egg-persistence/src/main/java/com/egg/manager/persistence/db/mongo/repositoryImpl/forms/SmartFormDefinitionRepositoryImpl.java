package com.egg.manager.persistence.db.mongo.repositoryImpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
public class SmartFormDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormDefinitionMO, String>
        implements SmartFormDefinitionRepository {


}
