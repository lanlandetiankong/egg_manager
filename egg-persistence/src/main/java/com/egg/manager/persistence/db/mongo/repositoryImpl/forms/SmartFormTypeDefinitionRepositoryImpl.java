package com.egg.manager.persistence.db.mongo.repositoryImpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Repository
public class SmartFormTypeDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormTypeDefinitionMO, String> implements SmartFormTypeDefinitionRepository {

}
