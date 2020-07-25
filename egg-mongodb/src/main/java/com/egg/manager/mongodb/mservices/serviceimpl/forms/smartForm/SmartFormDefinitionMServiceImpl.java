package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormDefinitionMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormDefinitionMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service
public class SmartFormDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormDefinitionRepository,SmartFormDefinitionMO,String>
        implements SmartFormDefinitionMService {


}
