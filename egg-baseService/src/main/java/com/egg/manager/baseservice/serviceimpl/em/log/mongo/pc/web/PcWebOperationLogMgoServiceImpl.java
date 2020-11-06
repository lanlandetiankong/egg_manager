package com.egg.manager.baseservice.serviceimpl.em.log.mongo.pc.web;

import com.egg.manager.api.services.em.log.basic.pc.web.PcWebOperationLogMgoService;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebOperationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description 操作日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class PcWebOperationLogMgoServiceImpl extends MyBaseMgoServiceImpl<PcWebOperationLogRepository, PcWebOperationLogMgo, Long>
        implements PcWebOperationLogMgoService {


}
