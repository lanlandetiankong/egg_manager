package com.egg.manager.baseservice.serviceimpl.em.log.mongo.pc.web;

import com.egg.manager.api.services.em.log.basic.pc.web.PcWebLoginLogMgoService;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebLoginLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description 登录日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class PcWebLoginLogMgoServiceImpl extends MyBaseMgoServiceImpl<PcWebLoginLogRepository, PcWebLoginLogMgo, String>
        implements PcWebLoginLogMgoService {


}
