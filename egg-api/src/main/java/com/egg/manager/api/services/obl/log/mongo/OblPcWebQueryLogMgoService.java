package com.egg.manager.api.services.obl.log.mongo;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebQueryLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description OolongBlog-查询日志-MService
 * @date 2020/10/20
 */
@Repository
public interface OblPcWebQueryLogMgoService extends MyBaseMgoService<OblPcWebQueryLogMgo, String> {


}
