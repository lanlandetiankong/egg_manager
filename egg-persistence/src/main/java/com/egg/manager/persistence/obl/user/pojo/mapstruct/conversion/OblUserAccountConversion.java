package com.egg.manager.persistence.obl.user.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 用户表-Conversion
 * @date 2020-12-07
 */
@Component
@Named("oblUserAccountConversion")
public class OblUserAccountConversion extends MyBaseMysqlConversion {

}