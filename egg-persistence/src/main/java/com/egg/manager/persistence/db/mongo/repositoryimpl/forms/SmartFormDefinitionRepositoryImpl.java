package com.egg.manager.persistence.db.mongo.repositoryimpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
public class SmartFormDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormDefinitionMgo, String>
        implements SmartFormDefinitionRepository {


}
