package com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental;

import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.EmDefineTenantMapstruct;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.EmUserAccountMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description 可在该接口写公用的转化方法, 定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMysqlConstantMapstruct<E, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto> {

    EmUserAccountMapstruct USER_ACCOUNT_MAPSTRUCT = EmUserAccountMapstruct.INSTANCE;
    EmDefineTenantMapstruct DEFINE_TENANT_MAPSTRUCT = EmDefineTenantMapstruct.INSTANCE;

    /**
     * 用户账号 默认 用户类型
     * @return
     */
    default Integer handleGetUserAccountDefaultUserType() {
        return UserAccountBaseTypeEnum.SimpleUser.getValue();
    }

    /**
     * 用户账号 默认 状态值
     * @return
     */
    default Short handleGetUserAccountDefaultState() {
        return BaseStateEnum.ENABLED.getValue();
    }

    /**
     * 用户账号 默认是否锁定 值
     * @return
     */
    default Short handleGetUserAccountDefaultLocked() {
        return SwitchStateEnum.Close.getValue();
    }

}
