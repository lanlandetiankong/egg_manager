package com.egg.manager.persistence.db.mongo.repositoryimpl.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class PcWebQueryLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebQueryLogMgo, Long> implements PcWebQueryLogRepository {


}
