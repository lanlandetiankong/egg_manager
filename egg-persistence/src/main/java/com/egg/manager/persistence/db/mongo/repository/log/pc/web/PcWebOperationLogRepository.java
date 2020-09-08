package com.egg.manager.persistence.db.mongo.repository.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMO;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-PcWebOperationLogMO 表单定义-dao
 */
@Repository
public interface PcWebOperationLogRepository extends MyBaseMongoRepository<PcWebOperationLogMO, String> {

}
