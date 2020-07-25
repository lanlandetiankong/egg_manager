package com.egg.manager.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormDefinitionMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Repository
public interface SmartFormDefinitionMService extends MyBaseMongoService<SmartFormDefinitionMO,String> {


}
