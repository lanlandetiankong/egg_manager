package com.egg.manager.baseService.services.mongodb.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormRecordMService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormRecordRepository;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormRecordMService.class)
public class SmartFormRecordMServiceImpl extends MyBaseMongoServiceImpl<SmartFormRecordRepository, SmartFormRecordMO,String>
        implements SmartFormRecordMService {


}
