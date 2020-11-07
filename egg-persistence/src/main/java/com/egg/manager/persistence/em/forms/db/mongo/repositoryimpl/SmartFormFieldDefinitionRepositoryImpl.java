package com.egg.manager.persistence.em.forms.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormFieldDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
@Component
public class SmartFormFieldDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormFieldDefinitionMgo, String>
        implements SmartFormFieldDefinitionRepository {


}
