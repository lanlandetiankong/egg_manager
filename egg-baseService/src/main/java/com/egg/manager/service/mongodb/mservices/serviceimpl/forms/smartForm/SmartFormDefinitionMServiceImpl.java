package com.egg.manager.service.mongodb.mservices.serviceimpl.forms.smartForm;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormDefinitionMService;
import com.egg.manager.common.base.constant.mongodb.MongoModelFieldConstant;
import com.egg.manager.service.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mongo.dao.forms.SmartFormDefinitionRepository;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * MongoDb-FormDefinitionMO 表单定义-MService
 */
@Service(interfaceClass = SmartFormDefinitionMService.class)
public class SmartFormDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormDefinitionRepository,SmartFormDefinitionMO,String>
        implements SmartFormDefinitionMService {

    @Autowired
    private SmartFormDefinitionRepository smartFormDefinitionRepository ;

    @Override
    public Long updateFormTypeByTypeId(UserAccount userAccount,SmartFormTypeDefinitionMO formTypeDefinitionMO) {
        //表单类型id匹配的
        Query query = new Query().addCriteria(Criteria.where("formType."+ MongoModelFieldConstant.FIELD_FID).is(formTypeDefinitionMO.getFid()));
        Update update = new Update() ;
        update.set("formType",formTypeDefinitionMO);
        return smartFormDefinitionRepository.batchUpdate(query,update);
    }
}
