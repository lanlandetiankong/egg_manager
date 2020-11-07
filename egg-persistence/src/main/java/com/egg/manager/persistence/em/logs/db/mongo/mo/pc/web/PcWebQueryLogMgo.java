package com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 查询日志表
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "em_pc_web_query_log")
public class PcWebQueryLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 1L;






}
