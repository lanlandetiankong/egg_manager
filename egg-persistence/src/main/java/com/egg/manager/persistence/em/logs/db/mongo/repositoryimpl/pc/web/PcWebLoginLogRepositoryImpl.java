package com.egg.manager.persistence.em.logs.db.mongo.repositoryimpl.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class PcWebLoginLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebLoginLogMgo, String> implements PcWebLoginLogRepository {


}
