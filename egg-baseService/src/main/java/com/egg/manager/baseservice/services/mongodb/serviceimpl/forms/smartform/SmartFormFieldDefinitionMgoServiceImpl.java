package com.egg.manager.baseservice.services.mongodb.serviceimpl.forms.smartform;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormFieldDefinitionMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormFieldDefinitionRepository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormFieldDefinitionMgoService.class)
public class SmartFormFieldDefinitionMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMgo, String>
        implements SmartFormFieldDefinitionMgoService {


}