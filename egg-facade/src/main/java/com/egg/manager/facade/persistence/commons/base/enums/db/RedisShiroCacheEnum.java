package com.egg.manager.facade.persistence.commons.base.enums.db;

import com.egg.manager.facade.persistence.commons.base.constant.db.redis.RedisShiroKeyConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @ClassName: RedisShiroCacheEnum
 * @Author: zhoucj
 * @Date: 2020/10/23 14:50
 */
@Getter
@AllArgsConstructor
public enum RedisShiroCacheEnum {
    /**
     * 缓存配置-用户授权值
     */
    authorization(RedisShiroKeyConstant.KEY_AUTHORIZATION, 21600),
    /**
     * 缓存配置-token
     */
    token(RedisShiroKeyConstant.KEY_TOKEN, 21600),
    /**
     * 缓存配置-分组定义
     */
    defineGroup(RedisShiroKeyConstant.KEY_DEFINEGROUP, 21600),
    /**
     * 缓存配置-菜单定义
     */
    defineMenu(RedisShiroKeyConstant.KEY_DEFINE_MENU, 21600),
    /**
     * 缓存配置-角色定义
     */
    defineRole(RedisShiroKeyConstant.KEY_DEFINE_ROLE, 21600),
    /**
     * 缓存配置-权限定义
     */
    definePermission(RedisShiroKeyConstant.KEY_DEFINE_PERMISSION, 21600),
    /**
     * 缓存配置-所有分组定义
     */
    defineGroupAll(RedisShiroKeyConstant.KEY_DEFINEGROUP_ALL, 21600),
    /**
     * 缓存配置-所有菜单定义
     */
    defineMenuAll(RedisShiroKeyConstant.KEY_DEFINE_MENU_ALL, 21600),
    /**
     * 缓存配置-所有角色定义
     */
    defineRoleAll(RedisShiroKeyConstant.KEY_DEFINE_ROLE_ALL, 21600),
    /**
     * 缓存配置-所有权限定义
     */
    definePermissionAll(RedisShiroKeyConstant.KEY_DEFINE_PERMISSION_ALL, 21600),
    /**
     * 缓存配置-角色&权限
     */
    rolePermission(RedisShiroKeyConstant.KEY_ROLE_PERMISSION, 21600),
    /**
     * 缓存配置-用户&认证
     */
    userAuthorization(RedisShiroKeyConstant.KEY_USER_AUTHORIZATION, 21600),
    /**
     * 缓存配置-用户账号
     */
    userAccount(RedisShiroKeyConstant.KEY_USER_ACCOUNT, 21600),
    /**
     * 缓存配置-用户账号id
     */
    userAccountId(RedisShiroKeyConstant.KEY_USER_ACCOUNT_ID, 21600),
    /**
     * 缓存配置-用户token
     */
    userToken(RedisShiroKeyConstant.KEY_USER_TOKEN, 21600),
    /**
     * 缓存配置-用户&分组
     */
    userGroup(RedisShiroKeyConstant.KEY_USER_GROUP, 21600),
    /**
     * 缓存配置-用户&租户
     */
    userTenant(RedisShiroKeyConstant.KEY_USER_TENANT, 21600),
    /**
     * 缓存配置-用户&部门
     */
    userDepartment(RedisShiroKeyConstant.KEY_USER_DEPARTMENT, 21600),
    /**
     * 缓存配置-用户&角色
     */
    userRoles(RedisShiroKeyConstant.KEY_USER_ROLE, 21600),
    /**
     * 缓存配置-用户&权限
     */
    userPermissions(RedisShiroKeyConstant.KEY_USER_PERMISSION, 21600),
    /**
     * 缓存配置-前端-用户拥有的权限
     */
    userFrontMenus(RedisShiroKeyConstant.KEY_USER_FRONT_MENUS, 21600),
    /**
     * 缓存配置-前端-用户可访问的RouterUrl
     */
    userFrontRouterUrl(RedisShiroKeyConstant.KEY_USER_FRONT_ROUTER_URL, 21600),
    /**
     * 缓存配置-前端-用户可点击的按钮
     */
    userFrontButtons(RedisShiroKeyConstant.KEY_USER_FRONT_BUTTONS, 21600),
    ;

    /**
     * 存储的key
     */
    private String key;
    /**
     * 过期时间，单位:秒
     */
    private long ttl;

}
