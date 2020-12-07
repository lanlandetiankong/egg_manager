package com.egg.manager.persistence.em.user.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description app用户关联表-Conversion
 * @date 2020-12-07
 */
@Component
@Named("emUserAppRelatedConversion")
public class EmUserAppRelatedConversion extends MyBaseMysqlConversion {

}