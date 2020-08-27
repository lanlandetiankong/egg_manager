package com.egg.manager.persistence.db.mongo.daoimpl.forms;

import com.egg.manager.persistence.db.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.db.mongo.daoimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-dao
 */
@Repository
public class SmartFormTypeDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormTypeDefinitionMO, String> implements SmartFormTypeDefinitionRepository {

}
