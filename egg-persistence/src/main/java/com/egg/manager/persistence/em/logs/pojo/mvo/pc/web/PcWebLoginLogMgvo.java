package com.egg.manager.persistence.em.logs.pojo.mvo.pc.web;

import com.egg.manager.persistence.em.logs.pojo.mvo.pc.MyBaseWebLogMgvo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhoucj
 * @description 登录日志表 - MongoDB
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class PcWebLoginLogMgvo extends MyBaseWebLogMgvo<String> {

}
