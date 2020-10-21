package com.egg.manager.api.services.mongodb.mservices.service.log;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:操作日志-MService
 * @date 2020/10/20
 */
@Repository
public interface OperationLogMgoService extends MyBaseMgoService<PcWebQueryLogMgo, String> {


}
