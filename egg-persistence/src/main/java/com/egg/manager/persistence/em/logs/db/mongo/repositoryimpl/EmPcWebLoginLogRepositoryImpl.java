package com.egg.manager.persistence.em.logs.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.EmPcWebLoginLogRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class EmPcWebLoginLogRepositoryImpl extends MyBaseMongoRepositoryImpl<EmPcWebLoginLogMgo, String> implements EmPcWebLoginLogRepository {


}
