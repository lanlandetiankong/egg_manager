package com.egg.manager.mongodb.mservices.service.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.MyBaseMongoService;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-MService
 */
@Repository
public interface SmartFormTypeDefinitionMService extends MyBaseMongoService<SmartFormTypeDefinitionMO,String> {

}
