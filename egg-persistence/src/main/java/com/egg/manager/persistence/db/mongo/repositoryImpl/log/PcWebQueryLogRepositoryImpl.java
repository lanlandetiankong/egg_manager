package com.egg.manager.persistence.db.mongo.repositoryImpl.log;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import com.egg.manager.persistence.db.mongo.repositoryImpl.MyBaseMongoRepositoryImpl;
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
public class PcWebQueryLogRepositoryImpl extends MyBaseMongoRepositoryImpl<PcWebQueryLogMO, String> implements PcWebQueryLogRepository {


}
