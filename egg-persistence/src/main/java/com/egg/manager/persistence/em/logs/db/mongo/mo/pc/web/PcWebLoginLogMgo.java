package com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 登录日志表
 * @date 2020/10/20
 */
@Data
@Document(collection = "em_pc_web_login_log")
@EqualsAndHashCode(callSuper=true)
public class PcWebLoginLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 1L;

}
