package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.egg.manager.api.mongodb.mservices.service.forms.smartForm.SmartFormFieldDefinitionMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormFieldDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormFieldDefinitionMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service
public class SmartFormFieldDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMO,String>
        implements SmartFormFieldDefinitionMService {


}
