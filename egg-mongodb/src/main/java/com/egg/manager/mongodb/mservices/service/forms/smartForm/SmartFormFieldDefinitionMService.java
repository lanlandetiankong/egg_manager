package com.egg.manager.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormFieldDefinitionMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单字段定义-MService
 */
@Repository
public interface SmartFormFieldDefinitionMService extends MyBaseMongoService<SmartFormFieldDefinitionMO,String> {


}
