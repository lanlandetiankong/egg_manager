package com.egg.manager.persistence.obl.log.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description OolongBlog-操作日志表-Mgo
 * @date 2020/10/20
 */
@Data
@Document(collection = "obl_pc_web_operation_log")
@EqualsAndHashCode(callSuper = true)
public class OblPcWebOperationLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 1L;
}
