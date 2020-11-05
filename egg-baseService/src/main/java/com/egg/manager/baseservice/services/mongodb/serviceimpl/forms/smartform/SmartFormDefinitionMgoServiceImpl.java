package com.egg.manager.baseservice.services.mongodb.serviceimpl.forms.smartform;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormDefinitionMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.commons.base.constant.mongodb.MongoModelFieldConstant;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormDefinitionRepository;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author zhoucj
 * @description: 表单定义-MService
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormDefinitionMgoService.class)
public class SmartFormDefinitionMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormDefinitionRepository, SmartFormDefinitionMgo, Long>
        implements SmartFormDefinitionMgoService {

    @Autowired
    private SmartFormDefinitionRepository smartFormDefinitionRepository;

    @Override
    public Long updateFormTypeByTypeId(UserAccount userAccount, SmartFormTypeDefinitionMgo smartFormTypeDefinitionMgo) {
        //表单类型id匹配的
        Query query = new Query().addCriteria(Criteria.where("formType." + MongoModelFieldConstant.FIELD_FID).is(smartFormTypeDefinitionMgo.getFid()));
        Update update = new Update();
        update.set("formType", smartFormTypeDefinitionMgo);
        return smartFormDefinitionRepository.batchUpdate(query, update);
    }
}
