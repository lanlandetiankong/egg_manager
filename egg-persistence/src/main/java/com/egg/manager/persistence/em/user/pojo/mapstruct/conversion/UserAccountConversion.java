package com.egg.manager.persistence.em.user.pojo.mapstruct.conversion;

import com.egg.manager.persistence.enhance.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("userAccountConversion")
public class UserAccountConversion extends MyBaseMysqlConversion {

}
