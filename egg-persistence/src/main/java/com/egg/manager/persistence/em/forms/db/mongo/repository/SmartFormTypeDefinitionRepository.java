package com.egg.manager.persistence.em.forms.db.mongo.repository;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.expand.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description: 表单类型定义-dao
 * @date 2020/10/21
 */
@Component
public interface SmartFormTypeDefinitionRepository extends MyBaseMongoRepository<SmartFormTypeDefinitionMgo, Long> {

}
