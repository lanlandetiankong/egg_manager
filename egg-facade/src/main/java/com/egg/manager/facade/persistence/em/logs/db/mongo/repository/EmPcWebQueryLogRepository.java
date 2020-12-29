package com.egg.manager.facade.persistence.em.logs.db.mongo.repository;

import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.facade.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 查询日志表-Repository
 * @date 2020/10/21
 */
@Repository
public interface EmPcWebQueryLogRepository extends MyBaseMongoRepository<EmPcWebQueryLogMgo, String> {

}
