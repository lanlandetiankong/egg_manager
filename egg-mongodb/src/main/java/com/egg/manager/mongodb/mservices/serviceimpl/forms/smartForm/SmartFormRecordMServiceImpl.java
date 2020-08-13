package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.mongodb.mservices.service.forms.smartForm.SmartFormRecordMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormRecordRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormRecordMO;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormRecordMService.class)
public class SmartFormRecordMServiceImpl extends MyBaseMongoServiceImpl<SmartFormRecordRepository, SmartFormRecordMO,String>
        implements SmartFormRecordMService {


}
