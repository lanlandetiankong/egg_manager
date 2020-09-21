package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend;

import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserAccountMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import org.mapstruct.MapperConfig;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * <p>
 * 枚举相关方法 default定义
 * Notes:
 *
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMysqlEnumMapstruct<E, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto> {

    UserAccountMapstruct userAccountMapstruct = UserAccountMapstruct.INSTANCE;
    DefineTenantMapstruct defineTenantMapstruct = DefineTenantMapstruct.INSTANCE;

    /**
     * 判断值 是否为 启用
     *
     * @param value
     * @return
     */
    default boolean handleSwitchStateGetBoolean(Short value) {
        return SwitchStateEnum.Open.getValue() == value;
    }

    /**
     * 开关式枚举 取得值
     *
     * @param value
     * @return
     */
    default Short handleSwitchStateGetShort(Boolean value) {
        return (value == true) ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
    }

    /**
     * 开关式枚举 取得名称
     *
     * @param value
     * @return
     */
    default String handleSwitchStateGetName(Short value) {
        return SwitchStateEnum.dealGetNameByVal(value);
    }

    /**
     * 性别 枚举 取得名称
     *
     * @param value
     * @return
     */
    default String handleUserSexGetName(Short value) {
        return UserSexEnum.dealGetNameByVal(value);
    }

    /**
     * 性别 枚举 取得值
     *
     * @param value
     * @return
     */
    default Short handleUserSexGetValue(String value) {
        return UserSexEnum.dealGetValByName(value);
    }

    /**
     * 用户状态 枚举 取得名称
     *
     * @param value
     * @return
     */
    default String handleUserAccountStateGetInfo(Short value) {
        return UserAccountStateEnum.doGetEnumInfoByValue(value);
    }

    /**
     * 取得[用户类型]名称
     *
     * @param value
     * @return
     */
    default String handleUserTypeGetStr(Integer value) {
        UserAccountBaseTypeEnum userAccountBaseTypeEnums = UserAccountBaseTypeEnum.doGetEnumByValue(value);
        if (userAccountBaseTypeEnums != null) {
            return userAccountBaseTypeEnums.getLabel();
        }
        return "";
    }

    /**
     * 模块类型 取得label
     *
     * @param type
     * @return
     */
    default String handleDefineModuleTypeGetLabel(Integer type) {
        if (type != null) {
            DefineModuleTypeEnum typeEnum = DefineModuleTypeEnum.doGetEnumByValue(type);
            if (typeEnum != null) {
                return typeEnum.getLabel();
            }
        }
        return "";
    }

    /**
     * 取得 角色类型 label
     *
     * @param type
     * @return
     */
    default String handleDefineRoleTypeGetLabel(Integer type) {
        if (type != null) {
            DefineRoleTypeEnum typeEnum = DefineRoleTypeEnum.doGetEnumByValue(type);
            if (typeEnum != null) {
                return typeEnum.getLabel();
            }
        }
        return "";
    }

    /**
     * 权限 类型 取得 label
     *
     * @param value
     * @return
     */
    default String handleDefinePermissionTypeGetLabel(Integer value) {
        if (value == null) {
            return "";
        }
        DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(value);
        return typeEnum == null ? "" : typeEnum.getLabel();
    }

    /**
     * 取得菜单跳转类型 label
     *
     * @param type
     * @return
     */
    default String handleDefineMenuUrlJumpTypeGetLabel(Integer type) {
        if (type != null) {
            DefineMenuUrlJumpTypeEnum typeEnum = DefineMenuUrlJumpTypeEnum.doGetEnumByValue(type);
            if (typeEnum != null) {
                return typeEnum.getLabel();
            }
        }
        return "";
    }

    /**
     * 取得 职务 类型label
     *
     * @param type
     * @return
     */
    default String handleDefineJobTypeGetGetLabel(Integer type) {
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(type);
        if (defineJobTypeEnum != null) {
            return defineJobTypeEnum.getLabel();
        }
        return "";
    }
}
