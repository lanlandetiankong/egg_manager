package com.egg.manager.persistence.db.mongo.daoimpl.forms;

import com.egg.manager.persistence.db.mongo.dao.forms.SmartFormDefinitionRepository;
import com.egg.manager.persistence.db.mongo.daoimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
public class SmartFormDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormDefinitionMO, String>
        implements SmartFormDefinitionRepository {


}
