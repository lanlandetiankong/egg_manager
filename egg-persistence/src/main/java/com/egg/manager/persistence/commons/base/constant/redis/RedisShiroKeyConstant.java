package com.egg.manager.persistence.commons.base.constant.redis;


/**
 * @author zhoucj
 * @description
 * @date 2019/10/5
 */
public interface RedisShiroKeyConstant {
    /**
     * shiro 缓存前缀
     */
    String SHIRO_CACHE_PREFIX = "shiro:cache:";
    /**
     * 缓存KEY-用户授权值
     */
    String KEY_AUTHORIZATION = SHIRO_CACHE_PREFIX + "jwt:user:authorization";
    /**
     * 缓存KEY-token
     */
    String KEY_TOKEN = SHIRO_CACHE_PREFIX + "token";
    /**
     * 缓存KEY-分组定义
     */
    String KEY_DEFINEGROUP = SHIRO_CACHE_PREFIX + "define:group";
    /**
     * 缓存KEY-菜单定义
     */
    String KEY_DEFINE_MENU = SHIRO_CACHE_PREFIX + "define:menu";
    /**
     * 缓存KEY-角色定义
     */
    String KEY_DEFINE_ROLE = SHIRO_CACHE_PREFIX + "define:role";
    /**
     * 缓存KEY-权限定义
     */
    String KEY_DEFINE_PERMISSION = SHIRO_CACHE_PREFIX + "define:permission";
    /**
     * 缓存KEY-所有分组定义
     */
    String KEY_DEFINEGROUP_ALL = SHIRO_CACHE_PREFIX + "define:group:all";
    /**
     * 缓存KEY-所有菜单定义
     */
    String KEY_DEFINE_MENU_ALL = SHIRO_CACHE_PREFIX + "define:menu:all";
    /**
     * 缓存KEY-所有角色定义
     */
    String KEY_DEFINE_ROLE_ALL = SHIRO_CACHE_PREFIX + "define:role:all";
    /**
     * 缓存KEY-所有权限定义
     */
    String KEY_DEFINE_PERMISSION_ALL = SHIRO_CACHE_PREFIX + "define:permission:all";
    /**
     * 缓存KEY-角色&权限
     */
    String KEY_ROLE_PERMISSION = SHIRO_CACHE_PREFIX + "role:permission";
    /**
     * 缓存KEY-用户&认证
     */
    String KEY_USER_AUTHORIZATION = SHIRO_CACHE_PREFIX + "user:authorization";
    /**
     * 缓存KEY-用户账号
     */
    String KEY_USER_ACCOUNT = SHIRO_CACHE_PREFIX + "user:account";
    /**
     * 缓存KEY-用户账号id
     */
    String KEY_USER_ACCOUNT_ID = SHIRO_CACHE_PREFIX + "user:accountId";
    /**
     * 缓存KEY-用户token
     */
    String KEY_USER_TOKEN = SHIRO_CACHE_PREFIX + "user:token";
    /**
     * 缓存KEY-用户&分组
     */
    String KEY_USER_GROUP = SHIRO_CACHE_PREFIX + "user:group";
    /**
     * 缓存KEY-用户&租户
     */
    String KEY_USER_TENANT = SHIRO_CACHE_PREFIX + "user:tenant";
    /**
     * 缓存KEY-用户&部门
     */
    String KEY_USER_DEPARTMENT = SHIRO_CACHE_PREFIX + "user:department";
    /**
     * 缓存KEY-用户&角色
     */
    String KEY_USER_ROLE = SHIRO_CACHE_PREFIX + "user:role";
    /**
     * 缓存KEY-用户&权限
     */
    String KEY_USER_PERMISSION = SHIRO_CACHE_PREFIX + "user:permission";
    /**
     * 缓存KEY-前端-用户拥有的权限
     */
    String KEY_USER_FRONT_MENUS = SHIRO_CACHE_PREFIX + "user:front:menus";
    /**
     * 缓存KEY-前端-用户可访问的RouterUrl
     */
    String KEY_USER_FRONT_ROUTER_URL = SHIRO_CACHE_PREFIX + "user:front:routerUrl";
    /**
     * 缓存KEY-前端-用户可点击的按钮
     */
    String KEY_USER_FRONT_BUTTONS = SHIRO_CACHE_PREFIX + "user:front:buttons";

}
