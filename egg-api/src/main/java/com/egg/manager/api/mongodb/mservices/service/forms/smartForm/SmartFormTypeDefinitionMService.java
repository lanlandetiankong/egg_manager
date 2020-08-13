package com.egg.manager.api.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.api.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-MService
 */
@Repository
public interface SmartFormTypeDefinitionMService extends MyBaseMongoService<SmartFormTypeDefinitionMO,String> {
    /**
     * 数据转枚举select
     * @param result
     */
    void dealResultListSetToEntitySelect(MyCommonResult<SmartFormTypeDefinitionMO> result,List<SmartFormTypeDefinitionMO> list);
}
