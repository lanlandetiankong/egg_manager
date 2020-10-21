package com.egg.manager.persistence.db.mongo.repository.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.MyBaseMongoRepository;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description: 表单类型定义-dao
 * @date 2020/10/21
 */
@Component
public interface SmartFormTypeDefinitionRepository extends MyBaseMongoRepository<SmartFormTypeDefinitionMgo, String> {

}
