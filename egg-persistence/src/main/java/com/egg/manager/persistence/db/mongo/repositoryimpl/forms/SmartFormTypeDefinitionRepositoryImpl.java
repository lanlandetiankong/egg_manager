package com.egg.manager.persistence.db.mongo.repositoryimpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormTypeDefinitionMgo 表单类型定义-dao
 */
@Repository
public class SmartFormTypeDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormTypeDefinitionMgo, String> implements SmartFormTypeDefinitionRepository {

}
