package com.egg.manager.persistence.em.logs.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 查询日志表
 * @date 2020/10/20
 */
@Data
@Document(collection = "em_pc_web_query_log")
@EqualsAndHashCode(callSuper = true)
public class EmPcWebQueryLogMgo extends MyBaseWebLogMgo<String> {


    private static final long serialVersionUID = 2694531010793097343L;
}
