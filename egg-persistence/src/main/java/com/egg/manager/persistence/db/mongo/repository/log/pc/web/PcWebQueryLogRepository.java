package com.egg.manager.persistence.db.mongo.repository.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-PcWebQueryLogMgo 表单定义-dao
 */
@Repository
public interface PcWebQueryLogRepository extends MyBaseMongoRepository<PcWebQueryLogMgo, String> {

}
