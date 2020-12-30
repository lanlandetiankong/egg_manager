package com.egg.manager.persistence.em.logs.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebOperationLogRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表-RepositoryImpl
 * @date 2020/10/21
 */
@Repository
public class EmPcWebOperationLogRepositoryImpl extends MyBaseMongoRepositoryImpl<EmPcWebOperationLogMgo, String> implements EmPcWebOperationLogRepository {


}
