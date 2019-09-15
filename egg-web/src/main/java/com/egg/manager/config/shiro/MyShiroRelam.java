package com.egg.manager.config.shiro;

import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.service.SpringContextBeanService;
import com.egg.manager.service.define.DefineMenuService;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.service.user.UserRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * \* note: TODO 还没开始写
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:02
 * \* Description:
 * \
 */
public class MyShiroRelam extends AuthorizingRealm {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private DefineMenuService defineMenuService;
    @Autowired
    private DefineRoleService defineRoleService;
    @Autowired
    private UserRoleService userRoleService;


    /**
     * 必须重写该方法，否则shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtShiroToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(userAccountService == null){
            this.userAccountService  = SpringContextBeanService.getBean(UserAccountService.class) ;
        }
        if(defineMenuService == null){
            this.defineMenuService  = SpringContextBeanService.getBean(DefineMenuService.class) ;
        }
        if(defineRoleService == null){
            this.defineRoleService  = SpringContextBeanService.getBean(DefineRoleService.class) ;
        }
        if(userRoleService == null){
            this.userRoleService  = SpringContextBeanService.getBean(UserRoleService.class) ;
        }
        String userAccountId = JWTUtil.getUserAccountId(principalCollection.toString()) ;
        UserAccount userAccount = userAccountService.selectById(userAccountId);
        List<UserRole> userRoles = userRoleService.selectByAccountId() ;


        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
