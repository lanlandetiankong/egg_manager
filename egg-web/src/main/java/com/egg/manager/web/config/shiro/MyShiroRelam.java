package com.egg.manager.web.config.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.service.redis.service.user.UserAccountRedisService;
import com.egg.manager.common.util.spring.SpringContextBeanUtil;
import com.egg.manager.common.base.constant.Constant;
import com.egg.manager.common.util.jwt.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:02
 * \* Description:
 * \
 */
public class MyShiroRelam extends AuthorizingRealm {

    @Reference
    private UserAccountRedisService userAccountRedisService;




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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //当前登录用户id
        String authorization =  principalCollection.toString();
        String userAccountId = JWTUtil.getUserAccountId(principalCollection.toString()) ;
        if (userAccountRedisService == null) {
            this.userAccountRedisService = SpringContextBeanUtil.getBean(UserAccountRedisService.class);
        }
        //取得 当前用户 有用的 角色、权限
        Set<String> roleSet = userAccountRedisService.dealGetCurrentUserAllRoleSet(authorization,userAccountId,false);
        Set<String> permissionSet = userAccountRedisService.dealGetCurrentUserAllPermissionSet(authorization,userAccountId,false);
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
        if(Constant.isPass){    //暂不
            //return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userAccountId = JWTUtil.getUserAccountId(token);
        if (userAccountId == null) {
            throw new UnauthorizedException("token invalid");
        }
        if (! JWTUtil.verify(token, userAccountId)) {
            throw new UnauthorizedException("JWT:账号信息不匹配！");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
