package com.egg.manager.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormRecordMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Repository
public interface SmartFormRecordMService extends MyBaseMongoService<SmartFormRecordMO,String> {


}
