package com.egg.manager.em.web.config.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.api.services.em.define.basic.EmDefinePermissionService;
import com.egg.manager.api.services.em.define.basic.EmDefineRoleService;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.util.jwt.JwtUtil;
import com.egg.manager.persistence.commons.util.basic.spring.SpringContextBeanUtil;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
    private EmDefineRoleService emDefineRoleService;
    @Reference
    private EmDefinePermissionService emDefinePermissionService;
    @Reference
    private EmDefineMenuService emDefineMenuService;
    @Reference
    private EmUserAccountService emUserAccountService;

    public MyShiroRelam(EmDefineRoleService emDefineRoleService, EmDefinePermissionService emDefinePermissionService,
                        EmDefineMenuService emDefineMenuService, EmUserAccountService emUserAccountService) {
        this.emDefineRoleService = emDefineRoleService;
        this.emDefinePermissionService = emDefinePermissionService;
        this.emDefineMenuService = emDefineMenuService;
        this.emUserAccountService = emUserAccountService;
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
        if (emDefineRoleService == null) {
            this.emDefineRoleService = SpringContextBeanUtil.getBean(EmDefineRoleService.class);
        }
        if (emDefinePermissionService == null) {
            this.emDefinePermissionService = SpringContextBeanUtil.getBean(EmDefinePermissionService.class);
        }
        if (emDefineMenuService == null) {
            this.emDefineMenuService = SpringContextBeanUtil.getBean(EmDefineMenuService.class);
        }
        if (emUserAccountService == null) {
            this.emUserAccountService = SpringContextBeanUtil.getBean(EmUserAccountService.class);
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
        CurrentLoginEmUserInfo loginUserInfo = emUserAccountService.queryDbToCacheable(userAccountId);
        if (loginUserInfo == null) {
            return simpleAuthorizationInfo;
        }
        //取得 当前用户 有用的 角色、权限
        Set<String> roleSet = emDefineRoleService.queryDbToCacheable(userAccountId);
        Set<String> permissionSet = emDefinePermissionService.queryDbToCacheable(userAccountId);
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
