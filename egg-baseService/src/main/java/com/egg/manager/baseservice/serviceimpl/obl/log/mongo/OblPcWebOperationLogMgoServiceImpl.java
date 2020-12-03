package com.egg.manager.baseservice.serviceimpl.obl.log.mongo;

import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebOperationLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebOperationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoucj
 * @description OolongBlog-操作日志-MService
 * @date 2020/10/21
 */
@Slf4j
@Service
public class OblPcWebOperationLogMgoServiceImpl extends MyBaseMgoServiceImpl<OblPcWebOperationLogRepository, OblPcWebOperationLogMgo, String>
        implements OblPcWebOperationLogMgoService {


}
