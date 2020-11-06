package com.egg.manager.api.services.em.forms.mongo.smartform;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhoucj
 * @description表单类型定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormTypeDefinitionMgoService extends MyBaseMgoService<SmartFormTypeDefinitionMgo, Long> {
    /**
     * 数据转枚举select
     * @param result
     * @param list
     * @return
     */
    MyCommonResult<SmartFormTypeDefinitionMgo> dealResultListToEnums(MyCommonResult<SmartFormTypeDefinitionMgo> result, List<SmartFormTypeDefinitionMgo> list);
}
