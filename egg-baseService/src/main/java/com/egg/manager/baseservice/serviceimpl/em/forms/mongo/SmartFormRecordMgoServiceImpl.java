package com.egg.manager.baseservice.serviceimpl.em.forms.mongo;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormRecordMgoService;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormRecordRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description 表单定义
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormRecordMgoService.class)
public class SmartFormRecordMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormRecordRepository, SmartFormRecordMgo, String>
        implements SmartFormRecordMgoService {


}
