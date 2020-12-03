package com.egg.manager.persistence.obl.log.db.mongo.repository;

import com.egg.manager.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description OolongBlog-操作日志表-Repository
 * @date 2020/10/21
 */
@Repository
public interface OblPcWebOperationLogRepository extends MyBaseMongoRepository<OblPcWebOperationLogMgo, String> {

}
