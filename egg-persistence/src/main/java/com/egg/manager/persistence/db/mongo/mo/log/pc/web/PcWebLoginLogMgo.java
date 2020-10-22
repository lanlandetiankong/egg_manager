package com.egg.manager.persistence.db.mongo.mo.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.MyBaseWebLogMgo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description: 登录日志表
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "em_pc_web_login_log")
public class PcWebLoginLogMgo extends MyBaseWebLogMgo<String> {

    private static final long serialVersionUID = 1L;

}
