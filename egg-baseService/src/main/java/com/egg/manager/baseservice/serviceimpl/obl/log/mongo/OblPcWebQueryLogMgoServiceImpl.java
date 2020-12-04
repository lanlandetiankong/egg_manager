package com.egg.manager.baseservice.serviceimpl.obl.log.mongo;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebOperationLogMgoService;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebQueryLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebQueryLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebQueryLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description OolongBlog-查询日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = OblPcWebQueryLogMgoService.class)
public class OblPcWebQueryLogMgoServiceImpl extends MyBaseMgoServiceImpl<OblPcWebQueryLogRepository, OblPcWebQueryLogMgo, String>
        implements OblPcWebQueryLogMgoService {


}
