package com.egg.manager.persistence.em.logs.db.mongo.repository;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
public interface EmPcWebLoginLogRepository extends MyBaseMongoRepository<EmPcWebLoginLogMgo, String> {

}
