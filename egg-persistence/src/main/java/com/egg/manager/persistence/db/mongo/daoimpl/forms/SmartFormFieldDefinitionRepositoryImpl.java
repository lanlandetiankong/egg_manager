package com.egg.manager.persistence.db.mongo.daoimpl.forms;

import com.egg.manager.persistence.db.mongo.dao.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.db.mongo.daoimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-dao
 */
@Repository
@Component
public class SmartFormFieldDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormFieldDefinitionMO, String>
        implements SmartFormFieldDefinitionRepository{


}
