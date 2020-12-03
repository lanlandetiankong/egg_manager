package com.egg.manager.persistence.obl.log.db.mongo.repositoryimpl;

import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebOperationLogRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class OblPcPcWebOperationLogRepositoryImpl extends MyBaseMongoRepositoryImpl<OblPcWebOperationLogMgo, String> implements OblPcWebOperationLogRepository {


}
