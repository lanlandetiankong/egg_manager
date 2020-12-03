package com.egg.manager.persistence.obl.log.pojo.mvo;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.MyBaseWebLogMgvo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhoucj
 * @description 登录日志表 - MongoDB
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OblPcWebLoginLogMgvo extends MyBaseWebLogMgvo<String> {

}
