package com.egg.manager.api.services.mongodb.mservices.service.log;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-PcWebQueryLogMgo 操作日志-MService
 */
@Repository
public interface OperationLogMgoService extends MyBaseMgoService<PcWebQueryLogMgo,String> {


}
