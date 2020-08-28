package com.egg.manager.service.mongodb.mservices.serviceimpl.log;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.log.OperationLogMService;
import com.egg.manager.service.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.dao.log.OperationLogRepository;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
@Service(interfaceClass = OperationLogMService.class)
public class OperationLogMServiceImpl extends MyBaseMongoServiceImpl<OperationLogRepository, OperationLogMO,String>
        implements OperationLogMService {


}
