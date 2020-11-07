package com.egg.manager.persistence.em.forms.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormDefinitionRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
public class SmartFormDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormDefinitionMgo, String>
        implements SmartFormDefinitionRepository {


}
