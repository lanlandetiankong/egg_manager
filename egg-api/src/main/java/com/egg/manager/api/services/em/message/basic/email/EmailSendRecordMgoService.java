package com.egg.manager.api.services.em.message.basic.email;

import com.egg.manager.api.exchange.services.mongo.MyBaseMgoService;
import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucj
 * @description 表单定义
 * @date 2020/10/20
 */
@Repository
public interface EmailSendRecordMgoService extends MyBaseMgoService<EmailSendRecordMgo, Long> {


}
