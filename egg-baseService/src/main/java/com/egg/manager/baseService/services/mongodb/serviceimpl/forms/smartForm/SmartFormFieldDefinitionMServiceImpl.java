package com.egg.manager.baseService.services.mongodb.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormFieldDefinitionMService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormFieldDefinitionRepository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormFieldDefinitionMService.class)
public class SmartFormFieldDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMO,String>
        implements SmartFormFieldDefinitionMService {


}
