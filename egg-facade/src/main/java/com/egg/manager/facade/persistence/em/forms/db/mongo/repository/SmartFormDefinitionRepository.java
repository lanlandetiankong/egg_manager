package com.egg.manager.facade.persistence.em.forms.db.mongo.repository;

import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.facade.persistence.exchange.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义-dao
 * @date 2020/10/21
 */
@Repository
@Component
public interface SmartFormDefinitionRepository extends MyBaseMongoRepository<SmartFormDefinitionMgo, String> {


}
