package com.egg.manager.baseservice.services.mongodb.serviceimpl.log;

import com.egg.manager.api.services.mongodb.mservices.service.log.OperationLogMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import org.springframework.stereotype.Service;

/**
 * MongoDb-PcWebQueryLogMgo 操作日志-MService
 */
@Service
public class OperationLogMgoServiceImpl extends MyBaseMgoServiceImpl<PcWebQueryLogRepository, PcWebQueryLogMgo, String>
        implements OperationLogMgoService {


}
