package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormRecordMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormRecordRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormRecordMO;
import org.springframework.stereotype.Service;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service
public class SmartFormRecordMServiceImpl extends MyBaseMongoServiceImpl<SmartFormRecordRepository, SmartFormRecordMO,String>
        implements SmartFormRecordMService {


}
