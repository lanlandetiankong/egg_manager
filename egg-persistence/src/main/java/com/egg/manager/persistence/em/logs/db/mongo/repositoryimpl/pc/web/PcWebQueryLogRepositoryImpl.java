package com.egg.manager.persistence.em.logs.db.mongo.repositoryimpl.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebQueryLogRepository;
import com.egg.manager.persistence.expand.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class PcWebQueryLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebQueryLogMgo, Long> implements PcWebQueryLogRepository {


}
