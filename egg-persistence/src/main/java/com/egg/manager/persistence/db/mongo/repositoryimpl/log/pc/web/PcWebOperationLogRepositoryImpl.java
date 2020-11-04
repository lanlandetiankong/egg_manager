package com.egg.manager.persistence.db.mongo.repositoryimpl.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebOperationLogRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class PcWebOperationLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebOperationLogMgo, Long> implements PcWebOperationLogRepository {


}
