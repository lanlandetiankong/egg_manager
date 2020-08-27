package com.egg.manager.api.mongodb.mservices.service.log;

import com.egg.manager.api.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
@Repository
public interface OperationLogMService extends MyBaseMongoService<OperationLogMO,String> {


}
