package com.egg.manager.persistence.db.mongo.repository.log;

import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-OperationLogMO 表单定义-dao
 */
@Repository
public interface OperationLogRepository extends MyBaseMongoRepository<OperationLogMO, String> {

}
