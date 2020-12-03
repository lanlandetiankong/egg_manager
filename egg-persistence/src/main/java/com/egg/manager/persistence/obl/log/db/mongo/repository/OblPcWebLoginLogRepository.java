package com.egg.manager.persistence.obl.log.db.mongo.repository;

import com.egg.manager.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
public interface OblPcWebLoginLogRepository extends MyBaseMongoRepository<OblPcWebLoginLogMgo, String> {

}
