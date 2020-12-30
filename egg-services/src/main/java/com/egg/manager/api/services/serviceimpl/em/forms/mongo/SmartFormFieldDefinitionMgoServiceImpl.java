package com.egg.manager.api.services.serviceimpl.em.forms.mongo;

import org.apache.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormFieldDefinitionMgoService;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormFieldDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormFieldDefinitionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description 表单定义-MService
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormFieldDefinitionMgoService.class)
public class SmartFormFieldDefinitionMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormFieldDefinitionRepository, SmartFormFieldDefinitionMgo, String>
        implements SmartFormFieldDefinitionMgoService {


}
