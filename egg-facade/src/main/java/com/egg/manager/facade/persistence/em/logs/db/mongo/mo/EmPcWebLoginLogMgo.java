package com.egg.manager.facade.persistence.em.logs.db.mongo.mo;

import com.egg.manager.facade.persistence.exchange.db.mongo.mo.MyBaseWebLogMgo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 登录日志表-Mgo
 * @date 2020/10/20
 */
@Data
@Document(collection = "em_pc_web_login_log")
@EqualsAndHashCode(callSuper = true)
public class EmPcWebLoginLogMgo extends MyBaseWebLogMgo<String> {


    private static final long serialVersionUID = -7485363553331688381L;
}
