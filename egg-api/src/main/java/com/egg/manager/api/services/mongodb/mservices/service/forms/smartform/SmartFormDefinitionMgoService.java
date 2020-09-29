package com.egg.manager.api.services.mongodb.mservices.service.forms.smartform;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMgo;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Repository
public interface SmartFormDefinitionMgoService extends MyBaseMgoService<SmartFormDefinitionMgo,String> {

    /**
     * 根据表单类型id更新表单中冗余的表单类型数据
     * @param userAccount 当前登录用户
     * @param formTypeDefinitionMO
     * @return
     */
    Long updateFormTypeByTypeId(UserAccount userAccount, SmartFormTypeDefinitionMgo formTypeDefinitionMO);


}
