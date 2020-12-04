package com.egg.manager.persistence.obl.log.pojo.mvo;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.MyBaseWebLogMgvo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhoucj
 * @description OolongBlog-登录日志表-Mgvo
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OblPcWebLoginLogMgvo extends MyBaseWebLogMgvo<String> {

    private static final long serialVersionUID = 3928998722055919841L;
}
