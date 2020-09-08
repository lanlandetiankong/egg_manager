package com.egg.manager.persistence.db.mongo.repository.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMO;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-PcWebQueryLogMO 表单定义-dao
 */
@Repository
public interface PcWebLoginLogRepository extends MyBaseMongoRepository<PcWebLoginLogMO, String> {

}
