package com.egg.manager.common.base.props.redis.shiro;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 18:24
 * \* Description:
 * \
 */
@Component
@ConfigurationProperties(prefix = "props.redis.shiro.cache")
@PropertySource("classpath:props-redis.properties")
public class RedisPropsOfShiroCache {


    /**
     * key
     */
    //token
    @Value("tokenKey")
    private String tokenKey ;
    //define
    @Value("defineGroupKey")
    private String defineGroupKey ;
    @Value("defineMenuKey")
    private String defineMenuKey ;
    @Value("defineRoleKey")
    private String defineRoleKey ;
    @Value("definePermissionKey")
    private String definePermissionKey ;
    @Value("defineGroupAllKey")
    private String defineGroupAllKey ;
    @Value("defineMenuAllKey")
    private String defineMenuAllKey ;
    @Value("defineRoleAllKey")
    private String defineRoleAllKey ;
    @Value("definePermissionAllKey")
    private String definePermissionAllKey ;
    //role
    @Value("rolePermissionKey")
    private String rolePermissionKey ;
    //user
    @Value("userAccountKey")
    private String userAccountKey ;
    @Value("userAccountIdKey")
    private String userAccountIdKey ;
    @Value("userTokenKey")
    private String userTokenKey ;
    @Value("userGroupKey")
    private String userGroupKey ;
    @Value("userRoleKey")
    private String userRoleKey ;
    @Value("userTenantKey")
    private String userTenantKey ;



    /**
     * ttl
     */

    //token
    @Value("tokenTtl")
    private String tokenTtl ;
    //define
    @Value("defineGroupTtl")
    private String defineGroupTtl ;
    @Value("defineMenuTtl")
    private String defineMenuTtl ;
    @Value("defineRoleTtl")
    private String defineRoleTtl ;
    @Value("definePermissionTtl")
    private String definePermissionTtl ;
    @Value("defineGroupAllTtl")
    private String defineGroupAllTtl ;
    @Value("defineMenuAllTtl")
    private String defineMenuAllTtl ;
    @Value("defineRoleAllTtl")
    private String defineRoleAllTtl ;
    @Value("definePermissionAllTtl")
    private String definePermissionAllTtl ;
    //role
    @Value("rolePermissionTtl")
    private String rolePermissionTtl ;
    //user
    @Value("userAccountTtl")
    private String userAccountTtl ;
    @Value("userAccountIdTtl")
    private String userAccountIdTtl ;
    @Value("userTokenTtl")
    private String userTokenTtl ;

    @Value("userGroupTtl")
    private String userGroupTtl ;
    @Value("userRoleTtl")
    private String userRoleTtl ;
    @Value("userTenantTtl")
    private String userTenantTtl ;

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public void setDefineGroupKey(String defineGroupKey) {
        this.defineGroupKey = defineGroupKey;
    }

    public void setDefineMenuKey(String defineMenuKey) {
        this.defineMenuKey = defineMenuKey;
    }

    public void setDefineRoleKey(String defineRoleKey) {
        this.defineRoleKey = defineRoleKey;
    }

    public void setDefinePermissionKey(String definePermissionKey) {
        this.definePermissionKey = definePermissionKey;
    }

    public void setRolePermissionKey(String rolePermissionKey) {
        this.rolePermissionKey = rolePermissionKey;
    }

    public void setUserAccountKey(String userAccountKey) {
        this.userAccountKey = userAccountKey;
    }

    public void setUserAccountIdKey(String userAccountIdKey) {
        this.userAccountIdKey = userAccountIdKey;
    }
    public void setUserTokenKey(String userTokenKey) {
        this.userTokenKey = userTokenKey;
    }

    public void setUserGroupKey(String userGroupKey) {
        this.userGroupKey = userGroupKey;
    }

    public void setUserRoleKey(String userRoleKey) {
        this.userRoleKey = userRoleKey;
    }
    public void setUserTenantKey(String userTenantKey) {
        this.userTenantKey = userTenantKey;
    }

    public void setTokenTtl(String tokenTtl) {
        this.tokenTtl = tokenTtl;
    }

    public void setDefineGroupTtl(String defineGroupTtl) {
        this.defineGroupTtl = defineGroupTtl;
    }

    public void setDefineMenuTtl(String defineMenuTtl) {
        this.defineMenuTtl = defineMenuTtl;
    }

    public void setDefineRoleTtl(String defineRoleTtl) {
        this.defineRoleTtl = defineRoleTtl;
    }

    public void setDefinePermissionTtl(String definePermissionTtl) {
        this.definePermissionTtl = definePermissionTtl;
    }

    public void setRolePermissionTtl(String rolePermissionTtl) {
        this.rolePermissionTtl = rolePermissionTtl;
    }

    public void setUserAccountTtl(String userAccountTtl) {
        this.userAccountTtl = userAccountTtl;
    }

    public void setUserAccountIdTtl(String userAccountIdTtl) {
        this.userAccountIdTtl = userAccountIdTtl;
    }

    public void setUserTokenTtl(String userTokenTtl) {
        this.userTokenTtl = userTokenTtl;
    }

    public void setUserGroupTtl(String userGroupTtl) {
        this.userGroupTtl = userGroupTtl;
    }

    public void setUserRoleTtl(String userRoleTtl) {
        this.userRoleTtl = userRoleTtl;
    }
    public void setUserTenantTtl(String userTenantTtl) {
        this.userTenantTtl = userTenantTtl;
    }

    public void setDefineGroupAllKey(String defineGroupAllKey) {
        this.defineGroupAllKey = defineGroupAllKey;
    }

    public void setDefineMenuAllKey(String defineMenuAllKey) {
        this.defineMenuAllKey = defineMenuAllKey;
    }

    public void setDefineRoleAllKey(String defineRoleAllKey) {
        this.defineRoleAllKey = defineRoleAllKey;
    }

    public void setDefinePermissionAllKey(String definePermissionAllKey) {
        this.definePermissionAllKey = definePermissionAllKey;
    }

    public void setDefineGroupAllTtl(String defineGroupAllTtl) {
        this.defineGroupAllTtl = defineGroupAllTtl;
    }

    public void setDefineMenuAllTtl(String defineMenuAllTtl) {
        this.defineMenuAllTtl = defineMenuAllTtl;
    }

    public void setDefineRoleAllTtl(String defineRoleAllTtl) {
        this.defineRoleAllTtl = defineRoleAllTtl;
    }

    public void setDefinePermissionAllTtl(String definePermissionAllTtl) {
        this.definePermissionAllTtl = definePermissionAllTtl;
    }




    /**
     * getter
     * @return
     */


    public String getDefineGroupAllKey() {
        return defineGroupAllKey;
    }

    public String getDefineMenuAllKey() {
        return defineMenuAllKey;
    }

    public String getDefineRoleAllKey() {
        return defineRoleAllKey;
    }

    public String getDefinePermissionAllKey() {
        return definePermissionAllKey;
    }

    public String getTokenKey() {
        return tokenKey.trim();
    }

    public String getDefineGroupKey() {
        return defineGroupKey.trim();
    }

    public String getDefineMenuKey() {
        return defineMenuKey;
    }

    public String getDefineRoleKey() {
        return defineRoleKey.trim();
    }

    public String getDefinePermissionKey() {
        return definePermissionKey.trim();
    }

    public String getRolePermissionKey() {
        return rolePermissionKey.trim();
    }

    public String getUserAccountKey() {
        return userAccountKey.trim();
    }

    public String getUserAccountIdKey() {
        return userAccountIdKey.trim();
    }

    public String getUserTokenKey() {
        return userTokenKey.trim();
    }

    public String getUserGroupKey() {
        return userGroupKey.trim();
    }

    public String getUserRoleKey() {
        return userRoleKey.trim();
    }
    public String getUserTenantKey() {
        return userTenantKey.trim();
    }

    public Long getTokenTtl() {
        return StringUtils.isBlank(tokenTtl) ? 0L : Long.parseLong(tokenTtl.trim()) ;
    }

    public Long getDefineGroupTtl() {
        return StringUtils.isBlank(defineGroupTtl) ? 0L : Long.parseLong(defineGroupTtl.trim()) ;
    }

    public Long getDefineMenuTtl() {
        return StringUtils.isBlank(defineMenuTtl) ? 0L : Long.parseLong(defineMenuTtl.trim()) ;
    }

    public Long getDefineRoleTtl() {
        return StringUtils.isBlank(defineRoleTtl) ? 0L : Long.parseLong(defineRoleTtl.trim()) ;
    }

    public Long getDefinePermissionTtl() {
        return StringUtils.isBlank(definePermissionTtl) ? 0L : Long.parseLong(definePermissionTtl.trim()) ;
    }

    public Long getRolePermissionTtl() {
        return StringUtils.isBlank(rolePermissionTtl) ? 0L : Long.parseLong(rolePermissionTtl.trim()) ;
    }

    public Long getUserAccountTtl() {
        return StringUtils.isBlank(userAccountTtl) ? 0L : Long.parseLong(userAccountTtl.trim()) ;
    }

    public Long getUserAccountIdTtl() {
        return StringUtils.isBlank(userAccountIdTtl) ? 0L : Long.parseLong(userAccountIdTtl.trim()) ;
    }
    public Long getUserTokenTtl() {
        return StringUtils.isBlank(userTokenTtl) ? 0L : Long.parseLong(userTokenTtl.trim()) ;
    }

    public Long getUserGroupTtl() {
        return StringUtils.isBlank(userGroupTtl) ? 0L : Long.parseLong(userGroupTtl.trim()) ;
    }

    public Long getUserRoleTtl() {
        return StringUtils.isBlank(userRoleTtl) ? 0L : Long.parseLong(userRoleTtl.trim()) ;
    }
    public Long getUserTenantTtl() {
        return StringUtils.isBlank(userTenantTtl) ? 0L : Long.parseLong(userTenantTtl.trim()) ;
    }

    public Long getDefineGroupAllTtl() {
        return StringUtils.isBlank(defineGroupAllTtl) ? 0L : Long.parseLong(defineGroupAllTtl.trim()) ;
    }

    public Long getDefineMenuAllTtl() {
        return StringUtils.isBlank(defineMenuAllTtl) ? 0L : Long.parseLong(defineMenuAllTtl.trim()) ;
    }

    public Long getDefineRoleAllTtl() {
        return StringUtils.isBlank(defineRoleAllTtl) ? 0L : Long.parseLong(defineRoleAllTtl.trim()) ;
    }

    public Long getDefinePermissionAllTtl() {
        return StringUtils.isBlank(definePermissionAllTtl) ? 0L : Long.parseLong(definePermissionAllTtl.trim()) ;
    }
}
