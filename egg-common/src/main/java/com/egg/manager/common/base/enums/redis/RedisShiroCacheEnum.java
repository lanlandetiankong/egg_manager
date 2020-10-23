package com.egg.manager.common.base.enums.redis;

import com.egg.manager.common.base.constant.redis.RedisShiroKeyConstant;

/**
 * @Description:
 * @ClassName: RedisShiroCacheEnum
 * @Author: zhoucj
 * @Date: 2020/10/23 14:50
 */
public enum RedisShiroCacheEnum {
    authorization(RedisShiroKeyConstant.KEY_AUTHORIZATION,21600),
    token(RedisShiroKeyConstant.KEY_TOKEN,21600),
    defineGroup(RedisShiroKeyConstant.KEY_DEFINEGROUP,21600),
    defineMenu(RedisShiroKeyConstant.KEY_DEFINE_MENU,21600),
    defineRole(RedisShiroKeyConstant.KEY_DEFINE_ROLE,21600),
    definePermission(RedisShiroKeyConstant.KEY_DEFINE_PERMISSION,21600),
    defineGroupAll(RedisShiroKeyConstant.KEY_DEFINEGROUP_ALL,21600),
    defineMenuAll(RedisShiroKeyConstant.KEY_DEFINE_MENU_ALL,21600),
    defineRoleAll(RedisShiroKeyConstant.KEY_DEFINE_ROLE_ALL,21600),
    definePermissionAll(RedisShiroKeyConstant.KEY_DEFINE_PERMISSION_ALL,21600),
    rolePermission(RedisShiroKeyConstant.KEY_ROLE_PERMISSION,21600),
    userAuthorization(RedisShiroKeyConstant.KEY_USER_AUTHORIZATION,21600),
    userAccount(RedisShiroKeyConstant.KEY_USER_ACCOUNT,21600),
    userAccountId(RedisShiroKeyConstant.KEY_USER_ACCOUNT_ID,21600),
    userToken(RedisShiroKeyConstant.KEY_USER_TOKEN,21600),
    userGroup(RedisShiroKeyConstant.KEY_USER_GROUP,21600),
    userTenant(RedisShiroKeyConstant.KEY_USER_TENANT,21600),
    userDepartment(RedisShiroKeyConstant.KEY_USER_DEPARTMENT,21600),
    userRoles(RedisShiroKeyConstant.KEY_USER_ROLE,21600),
    userPermissions(RedisShiroKeyConstant.KEY_USER_PERMISSION,21600),
    userFrontMenus(RedisShiroKeyConstant.KEY_USER_FRONT_MENUS,21600),
    userFrontRouterUrl(RedisShiroKeyConstant.KEY_USER_FRONT_ROUTER_URL,21600),
    userFrontButtons(RedisShiroKeyConstant.KEY_USER_FRONT_BUTTONS,21600),
    ;

    RedisShiroCacheEnum(String key, long ttl) {
        this.key = key;
        this.ttl = ttl;
    }

    /**
     * 存储的key
     */
    private String key;
    /**
     * 过期时间，单位:秒
     */
    private long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
