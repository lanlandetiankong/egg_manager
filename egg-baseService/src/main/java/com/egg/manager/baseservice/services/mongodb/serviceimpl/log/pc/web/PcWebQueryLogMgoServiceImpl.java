package com.egg.manager.baseservice.services.mongodb.serviceimpl.log.pc.web;

import com.egg.manager.api.services.mongodb.mservices.service.log.pc.web.PcWebQueryLogMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebQueryLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description: 操作日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class PcWebQueryLogMgoServiceImpl extends MyBaseMgoServiceImpl<PcWebQueryLogRepository, PcWebQueryLogMgo, Long>
        implements PcWebQueryLogMgoService {


}
