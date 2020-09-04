package com.egg.manager.persistence.pojo.mapstruct.mysql;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mapstruct.mysql.user.UserAccountMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import org.mapstruct.MapperConfig;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 *
 *
 * Notes:
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration=true)
public interface MyBaseMysqlConstantMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto> {

     UserAccountMapstruct userAccountVoMapstruct = UserAccountMapstruct.INSTANCE ;
     DefineTenantMapstruct defineTenantVoMapstruct = DefineTenantMapstruct.INSTANCE ;

    /**
     * 用户账号 默认 用户类型
     * @return
     */
    default Integer handleGetUserAccountDefaultUserType(){
        return UserAccountBaseTypeEnum.SimpleUser.getValue();
    }

    /**
     * 用户账号 默认 用户类型num
     * @return
     */
    default Integer handleGetUserAccountDefaultUserTypeNum(){
        return UserAccountBaseTypeEnum.SimpleUser.getValue();
    }

    /**
     * 用户账号 默认 状态值
     * @return
     */
    default Short handleGetUserAccountDefaultState(){
        return BaseStateEnum.ENABLED.getValue();
    }

    /**
     * 用户账号 默认是否锁定 值
     * @return
     */
    default Short handleGetUserAccountDefaultLocked(){
        return SwitchStateEnum.Close.getValue();
    }

}
