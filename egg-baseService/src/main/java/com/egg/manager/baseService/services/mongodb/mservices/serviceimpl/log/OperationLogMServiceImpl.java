package com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.log;

import com.egg.manager.api.services.mongodb.mservices.service.log.OperationLogMService;
import com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.repository.log.OperationLogRepository;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
//@Service(interfaceClass = OperationLogMService.class)
    @Service
public class OperationLogMServiceImpl extends MyBaseMongoServiceImpl<OperationLogRepository, OperationLogMO,String>
        implements OperationLogMService {


}
