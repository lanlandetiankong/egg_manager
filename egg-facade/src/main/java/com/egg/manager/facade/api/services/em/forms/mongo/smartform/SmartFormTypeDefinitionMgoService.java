package com.egg.manager.facade.api.services.em.forms.mongo.smartform;

import com.egg.manager.facade.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhoucj
 * @description表单类型定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormTypeDefinitionMgoService extends MyBaseMgoService<SmartFormTypeDefinitionMgo, String> {
    /**
     * 数据转枚举select
     * @param result
     * @param list
     * @return
     */
    WebResult dealResultListToEnums(WebResult result, List<SmartFormTypeDefinitionMgo> list);
}
