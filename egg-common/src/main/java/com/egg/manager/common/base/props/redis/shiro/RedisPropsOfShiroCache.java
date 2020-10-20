package com.egg.manager.common.base.props.redis.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 18:24
 * \* Description:
 * \
 */
@Data
@Component
@ConfigurationProperties(prefix = "props.redis.shiro.cache")
@PropertySource("classpath:universal/${egg.application.build.env}/props/props-redis.properties")
public class RedisPropsOfShiroCache  implements Serializable {
    /**
     * jwt
     */
    private String authorizationKey;

    /**
     * key->key
     */
    /**
     * key->token
     */
    private String tokenKey ;
    /**
     * key->define
     */
    private String defineGroupKey ;
    private String defineMenuKey ;
    private String defineRoleKey ;
    private String definePermissionKey ;
    private String defineGroupAllKey ;
    private String defineMenuAllKey ;
    private String defineRoleAllKey ;
    private String definePermissionAllKey ;

    private String rolePermissionKey ;
    /**
     * key->user
     */
    private String userAccountKey ;
    private String userAuthorizationKey ;
    private String userAccountIdKey ;
    private String userTokenKey ;
    private String userGroupKey ;
    private String userTenantKey ;
    private String UserDepartmentKey ;
    private String userRolesKey ;
    private String userPermissionsKey ;
    private String userFrontMenusKey ;
    private String userFrontRouterUrlKey ;
    private String userFrontButtonsKey ;




    /**
     * ttl->jwt
     */
    private long authorizationTtl ;
    /**
     * ttl->token
     */
    private long tokenTtl ;
    /**
     * ttl->define
     */
    private long defineGroupTtl ;
    private long defineMenuTtl ;
    private long defineRoleTtl ;
    private long definePermissionTtl ;
    private long defineGroupAllTtl ;
    private long defineMenuAllTtl ;
    private long defineRoleAllTtl ;
    private long definePermissionAllTtl ;
    /**
     * ttl->role
     */
    private long rolePermissionTtl ;
    /**
     * ttl->user
     */
    private long userAccountTtl ;
    private long userAuthorizationTtl ;
    private long userAccountIdTtl ;
    private long userTokenTtl ;
    private long userGroupTtl ;
    private long userTenantTtl ;
    private long userDepartmentTtl ;
    private long userRolesTtl ;
    private long userPermissionsTtl ;
    private long userFrontMenusTtl ;
    private long userFrontRouterUrlTtl ;
    private long userFrontButtonsTtl ;


}
