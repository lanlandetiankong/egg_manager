package com.egg.manager.facade.persistence.em.logs.db.mongo.mo;

import com.egg.manager.facade.persistence.exchange.db.mongo.mo.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 操作日志表
 * @date 2020/10/20
 */
@Data
@Document(collection = "em_pc_web_operation_log")
@EqualsAndHashCode(callSuper = true)
public class EmPcWebOperationLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 8543040620624303897L;
}
