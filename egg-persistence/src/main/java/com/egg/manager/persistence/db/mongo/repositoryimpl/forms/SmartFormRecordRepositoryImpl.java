package com.egg.manager.persistence.db.mongo.repositoryimpl.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormRecordRepository;
import com.egg.manager.persistence.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 表单记录-dao
 * @date 2020/10/21
 */
@Repository
@Component
public class SmartFormRecordRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormRecordMgo, Long>
        implements SmartFormRecordRepository {


}
