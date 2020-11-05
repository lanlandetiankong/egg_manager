package com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.enhance.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 表单定义-dao
 * @date 2020/10/21
 */
@Repository
public interface PcWebOperationLogRepository extends MyBaseMongoRepository<PcWebOperationLogMgo, Long> {

}
