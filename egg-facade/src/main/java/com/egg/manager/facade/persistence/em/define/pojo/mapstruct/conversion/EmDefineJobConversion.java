package com.egg.manager.facade.persistence.em.define.pojo.mapstruct.conversion;

import com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineJobConversion")
public class EmDefineJobConversion extends MyBaseMysqlConversion {

}
