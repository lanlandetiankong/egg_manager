package com.egg.manager.api.services.serviceimpl.em.log.mongo;

import org.apache.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.em.log.mongo.EmPcWebOperationLogMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebOperationLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description EggManager-操作日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = EmPcWebOperationLogMgoService.class)
public class EmPcWebOperationLogMgoServiceImpl extends MyBaseMgoServiceImpl<EmPcWebOperationLogRepository, EmPcWebOperationLogMgo, String>
        implements EmPcWebOperationLogMgoService {


}
