package com.egg.manager.mongodb.mservices.serviceimpl.log;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.mongodb.mservices.service.log.OperationLogMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.log.OperationLogRepository;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
@Service(interfaceClass = OperationLogMService.class)
public class OperationLogMServiceImpl extends MyBaseMongoServiceImpl<OperationLogRepository, OperationLogMO,String>
        implements OperationLogMService {


}
