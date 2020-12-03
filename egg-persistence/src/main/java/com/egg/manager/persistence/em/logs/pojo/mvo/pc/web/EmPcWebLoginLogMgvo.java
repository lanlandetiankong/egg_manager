package com.egg.manager.persistence.em.logs.pojo.mvo.pc.web;

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
public class EmPcWebLoginLogMgvo extends MyBaseWebLogMgvo<String> {

}
