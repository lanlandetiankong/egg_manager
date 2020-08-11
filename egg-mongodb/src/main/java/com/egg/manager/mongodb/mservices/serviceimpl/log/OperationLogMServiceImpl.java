package com.egg.manager.mongodb.mservices.serviceimpl.log;

import com.egg.manager.mongodb.mservices.service.log.OperationLogMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.log.OperationLogRepository;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-OperationLogMO 操作日志-MService
 */
@Service
public class OperationLogMServiceImpl extends MyBaseMongoServiceImpl<OperationLogRepository, OperationLogMO,String>
        implements OperationLogMService {


}
