package com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormFieldDefinitionMService;
import com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.dao.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormFieldDefinitionMService.class)
public class SmartFormFieldDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMO,String>
        implements SmartFormFieldDefinitionMService {


}
