package com.egg.manager.api.services.mongodb.mservices.service.forms.smartform;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMgo;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormDefinitionMO 表单字段定义-MService
 */
@Repository
public interface SmartFormFieldDefinitionMgoService extends MyBaseMgoService<SmartFormFieldDefinitionMgo,String> {


}
