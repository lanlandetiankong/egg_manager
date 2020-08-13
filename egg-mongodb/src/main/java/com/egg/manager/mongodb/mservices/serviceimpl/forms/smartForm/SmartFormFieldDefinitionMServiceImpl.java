package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.mongodb.mservices.service.forms.smartForm.SmartFormFieldDefinitionMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormFieldDefinitionMO;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormFieldDefinitionMService.class)
public class SmartFormFieldDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMO,String>
        implements SmartFormFieldDefinitionMService {


}
