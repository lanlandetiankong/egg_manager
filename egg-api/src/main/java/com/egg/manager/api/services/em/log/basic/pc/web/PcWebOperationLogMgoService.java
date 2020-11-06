package com.egg.manager.api.services.em.log.basic.pc.web;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description操作日志-MService
 * @date 2020/10/20
 */
@Repository
public interface PcWebOperationLogMgoService extends MyBaseMgoService<PcWebOperationLogMgo, Long> {


}
