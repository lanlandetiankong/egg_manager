package com.egg.manager.mongodb.mservices.service.log;

import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
@Repository
public interface OperationLogMService extends MyBaseMongoService<OperationLogMO,String> {


}
