package com.egg.manager.facade.api.services.em.forms.mongo.smartform;

import com.egg.manager.facade.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormFieldDefinitionMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description表单字段定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormFieldDefinitionMgoService extends MyBaseMgoService<SmartFormFieldDefinitionMgo, String> {


}
