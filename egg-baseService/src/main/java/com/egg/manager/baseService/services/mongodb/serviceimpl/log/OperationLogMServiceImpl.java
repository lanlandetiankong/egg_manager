package com.egg.manager.baseService.services.mongodb.serviceimpl.log;

import com.egg.manager.api.services.mongodb.mservices.service.log.OperationLogMService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import org.springframework.stereotype.Service;

/**
 * MongoDb-PcWebQueryLogMO 操作日志-MService
 */
//@Service(interfaceClass = OperationLogMService.class)
@Service
public class OperationLogMServiceImpl extends MyBaseMongoServiceImpl<PcWebQueryLogRepository, PcWebQueryLogMO, String>
        implements OperationLogMService {


}
