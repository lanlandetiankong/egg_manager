package com.egg.manager.persistence.obl.log.db.mongo.repositoryimpl;

import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebLoginLogRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 操作日志表 - MongoDB
 * @date 2020/10/21
 */
@Repository
public class OblPcPcWebLoginLogRepositoryImpl extends MyBaseMongoRepositoryImpl<OblPcWebLoginLogMgo, String> implements OblPcWebLoginLogRepository {


}
