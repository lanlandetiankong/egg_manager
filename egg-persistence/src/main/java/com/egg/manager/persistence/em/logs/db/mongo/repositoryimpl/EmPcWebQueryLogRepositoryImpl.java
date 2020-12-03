package com.egg.manager.persistence.em.logs.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebQueryLogRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class EmPcWebQueryLogRepositoryImpl extends MyBaseMongoRepositoryImpl<EmPcWebQueryLogMgo, String> implements EmPcWebQueryLogRepository {


}
