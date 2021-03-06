package com.egg.manager.api.services.serviceimpl.em.log.mongo;

import org.apache.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.em.log.mongo.EmPcWebLoginLogMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebLoginLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description EggManager-登录日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = EmPcWebLoginLogMgoService.class)
public class EmPcWebLoginLogMgoServiceImpl extends MyBaseMgoServiceImpl<EmPcWebLoginLogRepository, EmPcWebLoginLogMgo, String>
        implements EmPcWebLoginLogMgoService {


}
