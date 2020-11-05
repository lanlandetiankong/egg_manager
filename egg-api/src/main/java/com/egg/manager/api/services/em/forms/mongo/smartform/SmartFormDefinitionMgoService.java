package com.egg.manager.api.services.em.forms.mongo.smartform;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
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
