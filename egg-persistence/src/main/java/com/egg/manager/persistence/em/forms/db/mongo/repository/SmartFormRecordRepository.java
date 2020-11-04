package com.egg.manager.persistence.em.forms.db.mongo.repository;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.persistence.expand.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 表单记录-dao
 * @date 2020/10/21
 */
@Repository
@Component
public interface SmartFormRecordRepository extends MyBaseMongoRepository<SmartFormRecordMgo, Long> {


}
