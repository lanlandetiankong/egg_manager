package com.egg.manager.persistence.em.forms.db.mongo.repositoryimpl;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.exchange.db.mongo.repositoryimpl.MyBaseMongoRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description: 表单类型定义-dao
 * @date 2020/10/21
 */
@Repository
public class SmartFormTypeDefinitionRepositoryImpl extends MyBaseMongoRepositoryImpl<SmartFormTypeDefinitionMgo, Long> implements SmartFormTypeDefinitionRepository {

}
