package com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单字段定义-MService
 */
@Repository
public interface SmartFormFieldDefinitionMService extends MyBaseMongoService<SmartFormFieldDefinitionMO,String> {


}
