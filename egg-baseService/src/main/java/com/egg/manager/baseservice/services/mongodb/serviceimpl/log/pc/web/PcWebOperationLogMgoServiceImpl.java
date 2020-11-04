package com.egg.manager.baseservice.services.mongodb.serviceimpl.log.pc.web;

import com.egg.manager.api.services.mongodb.mservices.service.log.pc.web.PcWebOperationLogMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebOperationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description: 操作日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class PcWebOperationLogMgoServiceImpl extends MyBaseMgoServiceImpl<PcWebOperationLogRepository, PcWebOperationLogMgo, Long>
        implements PcWebOperationLogMgoService {


}
