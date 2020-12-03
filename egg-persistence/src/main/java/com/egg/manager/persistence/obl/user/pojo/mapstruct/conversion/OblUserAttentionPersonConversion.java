package com.egg.manager.persistence.obl.user.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Conversion
 * @date 2020-12-03
 */
@Component
@Named("oblUserAttentionPersonConversion")
public class OblUserAttentionPersonConversion extends MyBaseMysqlConversion {

}