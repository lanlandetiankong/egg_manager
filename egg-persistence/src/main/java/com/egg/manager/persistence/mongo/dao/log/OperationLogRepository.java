package com.egg.manager.persistence.mongo.dao.log;

import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-OperationLogMO 表单定义-dao
 */
@Repository
public interface OperationLogRepository extends MongoRepository<OperationLogMO, String> {

}
