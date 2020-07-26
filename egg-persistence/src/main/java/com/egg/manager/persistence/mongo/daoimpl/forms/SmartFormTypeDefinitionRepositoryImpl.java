package com.egg.manager.persistence.mongo.daoimpl.forms;

import com.egg.manager.persistence.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.mongo.daoimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Component
public class SmartFormTypeDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormTypeDefinitionMO,String> implements SmartFormTypeDefinitionRepository {

}
