package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormTypeDefinitionMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-MService
 */
@Service
public class SmartFormTypeDefinitionMServiceImpl  extends MyBaseMongoServiceImpl<SmartFormTypeDefinitionRepository, SmartFormTypeDefinitionMO,String>
        implements SmartFormTypeDefinitionMService {

}
