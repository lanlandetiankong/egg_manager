package com.egg.manager.persistence.mongo.daoimpl.log;

import com.egg.manager.persistence.mongo.dao.log.OperationLogRepository;
import com.egg.manager.persistence.mongo.daoimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
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
public class OperationLogRepositoryImpl extends MyBaseMongoRepositoryImpl<OperationLogMO,String> implements OperationLogRepository{


}
