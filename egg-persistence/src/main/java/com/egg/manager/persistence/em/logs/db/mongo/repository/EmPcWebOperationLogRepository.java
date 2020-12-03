package com.egg.manager.persistence.em.logs.db.mongo.repository;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
public interface EmPcWebOperationLogRepository extends MyBaseMongoRepository<EmPcWebOperationLogMgo, String> {

}
