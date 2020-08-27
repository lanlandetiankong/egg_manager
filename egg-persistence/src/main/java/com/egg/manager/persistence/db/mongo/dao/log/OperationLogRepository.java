package com.egg.manager.persistence.db.mongo.dao.log;

import com.egg.manager.persistence.db.mongo.dao.MyBaseMongoRepository;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-OperationLogMO 表单定义-dao
 */
@Repository
public interface OperationLogRepository extends MyBaseMongoRepository<OperationLogMO, String> {

}
