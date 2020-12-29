package com.egg.manager.facade.persistence.em.forms.db.mongo.repositoryimpl;

import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.facade.persistence.em.forms.db.mongo.repository.SmartFormRecordRepository;
import com.egg.manager.facade.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单记录-dao
 * @date 2020/10/21
 */
@Repository
@Component
public class SmartFormRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormRecordMgo, String>
        implements SmartFormRecordRepository {


}
