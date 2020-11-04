package com.egg.manager.api.services.mongodb.mservices.service.log.pc.web;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:操作日志-MService
 * @date 2020/10/20
 */
@Repository
public interface PcWebLoginLogMgoService extends MyBaseMgoService<PcWebLoginLogMgo, Long> {


}
