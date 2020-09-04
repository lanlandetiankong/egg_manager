package com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormRecordMService;
import com.egg.manager.baseService.services.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormRecordRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormRecordMService.class)
public class SmartFormRecordMServiceImpl extends MyBaseMongoServiceImpl<SmartFormRecordRepository, SmartFormRecordMO,String>
        implements SmartFormRecordMService {


}
