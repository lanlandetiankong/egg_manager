package com.egg.manager.baseservice.serviceimpl.obl.log.mongo;


import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebLoginLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebLoginLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description OolongBlog-登录日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class OblPcWebLoginLogMgoServiceImpl extends MyBaseMgoServiceImpl<OblPcWebLoginLogRepository, OblPcWebLoginLogMgo, String>
        implements OblPcWebLoginLogMgoService {


}
