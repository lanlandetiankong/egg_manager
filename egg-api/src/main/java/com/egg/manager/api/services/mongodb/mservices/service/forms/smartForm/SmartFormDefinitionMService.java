package com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Repository
public interface SmartFormDefinitionMService extends MyBaseMongoService<SmartFormDefinitionMO,String> {

    /**
     * 根据表单类型id更新表单中冗余的表单类型数据
     * @param userAccount 当前登录用户
     * @param formTypeDefinitionMO
     * @return
     */
    Long updateFormTypeByTypeId(UserAccount userAccount,SmartFormTypeDefinitionMO formTypeDefinitionMO);


}
