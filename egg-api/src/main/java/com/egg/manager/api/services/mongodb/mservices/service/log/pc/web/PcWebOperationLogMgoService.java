package com.egg.manager.api.services.mongodb.mservices.service.log.pc.web;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:操作日志-MService
 * @date 2020/10/20
 */
@Repository
public interface PcWebOperationLogMgoService extends MyBaseMgoService<PcWebOperationLogMgo, String> {


}