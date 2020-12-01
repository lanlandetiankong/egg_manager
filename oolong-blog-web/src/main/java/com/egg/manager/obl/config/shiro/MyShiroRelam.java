package com.egg.manager.obl.config.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.api.services.em.define.basic.DefinePermissionService;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.util.jwt.JwtUtil;
import com.egg.manager.persistence.commons.util.spring.SpringContextBeanUtil;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Component
public class MyShiroRelam extends AuthorizingRealm {

    @Reference
    private DefineRoleService defineRoleService;
    @Reference
    private DefinePermissionService definePermissionService;
    @Reference
    private DefineMenuService defineMenuService;
    @Reference
    private UserAccountService userAccountService;

    public MyShiroRelam(DefineRoleService defineRoleService, DefinePermissionService definePermissionService,
                        DefineMenuService defineMenuService, UserAccountService userAccountService) {
        this.defineRoleService = defineRoleService;
        this.definePermissionService = definePermissionService;
        this.defineMenuService = defineMenuService;
        this.userAccountService = userAccountService;
    }


    /**
     * 判断此Realm是否支持此Token
     * (配置shiro允许的 token 类型，修改为自定义的 TokenBean)
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtShiroToken;
    }

    public void initReference() {
        if (defineRoleService == null) {
            this.defineRoleService = SpringContextBeanUtil.getBean(DefineRoleService.class);
        }
        if (definePermissionService == null) {
            this.definePermissionService = SpringContextBeanUtil.getBean(DefinePermissionService.class);
        }
        if (defineMenuService == null) {
            this.defineMenuService = SpringContextBeanUtil.getBean(DefineMenuService.class);
        }
        if (userAccountService == null) {
            this.userAccountService = SpringContextBeanUtil.getBean(UserAccountService.class);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //初始化引用,避免service等为null的情况
        this.initReference();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //当前登录用户id
        String authorization = principalCollection.toString();
        String userAccountId = JwtUtil.getUserAccountId(principalCollection.toString());
        if (StringUtils.isBlank(authorization) || userAccountId == null) {
            return simpleAuthorizationInfo;
        }
        CurrentLoginUserInfo loginUserInfo = userAccountService.queryDbToCacheable(userAccountId);
        if (loginUserInfo == null) {
            return simpleAuthorizationInfo;
        }
        //取得 当前用户 有用的 角色、权限
        Set<String> roleSet = defineRoleService.queryDbToCacheable(userAccountId);
        Set<String> permissionSet = definePermissionService.queryDbToCacheable(userAccountId);
        simpleAuthorizationInfo.setRoles(roleSet);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 根据 Token 获取认证信息
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String userAccountId = JwtUtil.getUserAccountId(token);
        if (userAccountId == null) {
            throw new UnauthorizedException("token invalid");
        }
        if (!JwtUtil.verify(token, userAccountId)) {
            throw new UnauthorizedException("JWT:账号信息不匹配！");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
