package com.egg.manager.api.services.mongodb.mservices.service.log;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-PcWebQueryLogMO 操作日志-MService
 */
@Repository
public interface OperationLogMService extends MyBaseMongoService<PcWebQueryLogMO,String> {


}
