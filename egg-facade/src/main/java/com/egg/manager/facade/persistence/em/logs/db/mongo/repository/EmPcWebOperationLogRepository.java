package com.egg.manager.facade.persistence.em.logs.db.mongo.repository;

import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.facade.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表-Repository
 * @date 2020/10/21
 */
@Repository
public interface EmPcWebOperationLogRepository extends MyBaseMongoRepository<EmPcWebOperationLogMgo, String> {

}
