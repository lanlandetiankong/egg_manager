package com.egg.manager.persistence.mongo.dao.log;

import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie
 * @since 2020-07-19
 */
@Repository
public interface OperationLogDao extends MongoRepository<OperationLogMO, String> {

}
