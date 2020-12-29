package com.egg.manager.facade.persistence.em.logs.db.mongo.repositoryimpl;

import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.facade.persistence.em.logs.db.mongo.repository.EmPcWebQueryLogRepository;
import com.egg.manager.facade.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 查询日志表-RepositoryImpl
 * @date 2020/10/21
 */
@Repository
public class EmPcWebQueryLogRepositoryImpl extends MyBaseMongoRepositoryImpl<EmPcWebQueryLogMgo, String> implements EmPcWebQueryLogRepository {


}
