package com.egg.manager.baseservice.services.mongodb.serviceimpl.forms.smartform;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormRecordMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormRecordRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description: 表单定义
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormRecordMgoService.class)
public class SmartFormRecordMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormRecordRepository, SmartFormRecordMgo, Long>
        implements SmartFormRecordMgoService {


}
