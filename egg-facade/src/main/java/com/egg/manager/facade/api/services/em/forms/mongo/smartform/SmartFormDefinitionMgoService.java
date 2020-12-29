package com.egg.manager.facade.api.services.em.forms.mongo.smartform;

import com.egg.manager.facade.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description表单定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormDefinitionMgoService extends MyBaseMgoService<SmartFormDefinitionMgo, String> {

    /**
     * 根据表单类型id更新表单中冗余的表单类型数据
     * @param emUserAccountEntity   当前登录用户
     * @param formTypeDefinitionMgo
     * @return
     */
    Long updateFormTypeByTypeId(EmUserAccountEntity emUserAccountEntity, SmartFormTypeDefinitionMgo formTypeDefinitionMgo);


}
