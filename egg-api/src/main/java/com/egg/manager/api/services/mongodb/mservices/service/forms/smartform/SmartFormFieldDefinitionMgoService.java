package com.egg.manager.api.services.mongodb.mservices.service.forms.smartform;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormFieldDefinitionMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:表单字段定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormFieldDefinitionMgoService extends MyBaseMgoService<SmartFormFieldDefinitionMgo, Long> {


}
