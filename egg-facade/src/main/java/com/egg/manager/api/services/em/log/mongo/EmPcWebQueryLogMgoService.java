package com.egg.manager.api.services.em.log.mongo;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description EggManager-查询日志-MService
 * @date 2020/10/20
 */
@Repository
public interface EmPcWebQueryLogMgoService extends MyBaseMgoService<EmPcWebQueryLogMgo, String> {


}
