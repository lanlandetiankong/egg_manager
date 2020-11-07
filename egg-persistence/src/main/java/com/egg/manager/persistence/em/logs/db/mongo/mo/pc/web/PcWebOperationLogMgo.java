package com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 操作日志表
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "em_pc_web_operation_log")
public class PcWebOperationLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 1L;
}
