package com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web;

import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.MyBaseWebLogMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description: 操作日志表 - MongoDB
 * @date 2020/10/20
 */
@Data
public class PcWebOperationLogMgvo extends MyBaseWebLogMgvo<String> {

}
