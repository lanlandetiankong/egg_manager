package com.egg.manager.persistence.db.mongo.repositoryimpl.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class PcWebLoginLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebLoginLogMgo, String> implements PcWebLoginLogRepository {


}
