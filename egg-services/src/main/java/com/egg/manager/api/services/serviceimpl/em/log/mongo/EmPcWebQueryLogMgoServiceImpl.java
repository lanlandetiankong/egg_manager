package com.egg.manager.api.services.serviceimpl.em.log.mongo;

import org.apache.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.em.log.mongo.EmPcWebQueryLogMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebQueryLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description EggManager-查询日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = EmPcWebQueryLogMgoService.class)
public class EmPcWebQueryLogMgoServiceImpl extends MyBaseMgoServiceImpl<EmPcWebQueryLogRepository, EmPcWebQueryLogMgo, String>
        implements EmPcWebQueryLogMgoService {


}
