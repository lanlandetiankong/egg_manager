package com.egg.manager.api.services.em.forms.mongo.smartform;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description表单定义-MService
 * @date 2020/10/20
 */
@Repository
public interface SmartFormRecordMgoService extends MyBaseMgoService<SmartFormRecordMgo, Long> {


}
