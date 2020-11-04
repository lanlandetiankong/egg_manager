package com.egg.manager.api.services.mongodb.mservices.service.forms.smartform;

import com.egg.manager.api.services.mongodb.mservices.service.MyBaseMgoService;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description:表单定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormRecordMgoService extends MyBaseMgoService<SmartFormRecordMgo, Long> {


}
