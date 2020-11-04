package com.egg.manager.api.services.mongodb.mservices.service.forms.smartform;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMgo;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:表单定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormDefinitionMgoService extends MyBaseMgoService<SmartFormDefinitionMgo, Long> {

    /**
     * 根据表单类型id更新表单中冗余的表单类型数据
     * @param userAccount          当前登录用户
     * @param formTypeDefinitionMgo
     * @return
     */
    Long updateFormTypeByTypeId(UserAccount userAccount, SmartFormTypeDefinitionMgo formTypeDefinitionMgo);


}
