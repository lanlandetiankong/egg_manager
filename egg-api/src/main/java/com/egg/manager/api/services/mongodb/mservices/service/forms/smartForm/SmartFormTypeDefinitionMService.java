package com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
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
    MyCommonResult<SmartFormTypeDefinitionMO> dealResultListSetToEntitySelect(MyCommonResult<SmartFormTypeDefinitionMO> result,List<SmartFormTypeDefinitionMO> list);
}
