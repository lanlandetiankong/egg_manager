package com.egg.manager.persistence.db.mongo.repositoryimpl.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie
 * @since 2020-07-19
 */
@Repository
public class PcWebLoginLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebLoginLogMgo, String> implements PcWebLoginLogRepository {


}
