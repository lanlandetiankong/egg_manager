package com.egg.manager.api.utils.shiro;

import com.egg.manager.common.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @description:  shiro 工具类
 * @author zhoucj
 * @date 2020/10/27
 */
public class EggShiroUtil {

    /**
     * 获取当前 Subject
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 取得挡墙登录用户
     * @param isRequired 是否必要，当true且userAccount为null时抛出异常
     */
    public static UserAccount gainCurrentUser(boolean isRequired) {
        UserAccount user = (UserAccount) SecurityUtils.getSubject().getPrincipal();
        if(user == null && isRequired){
            throw new MyAuthenticationExpiredException();
        }
        return user;
    }


    /**
     * 取得挡墙登录用户id
     * @param isRequired 是否必要，当true且userAccount为null时抛出异常
     */
    public static String gainCurrentUserId(boolean isRequired) {
        UserAccount user = gainCurrentUser(isRequired);
        if(user == null && isRequired){
            throw new MyAuthenticationExpiredException();
        }
        if(StringUtils.isBlank(user.getFid()) && isRequired){
            throw new MyAuthenticationExpiredException();
        }
        return user.getFid();
    }

    /**
     * 验证当前用户是否属于该角色？
     *
     * @param roleName 角色名
     * @return 属于该角色：true，否则false
     */
    public static boolean hasRole(String roleName) {
        Subject subject = getSubject();
        if(subject == null){
            return false ;
        }
        if(StringUtils.isBlank(roleName)){
            return false ;
        }
        return subject.hasRole(roleName);
    }

    /**
     * 验证当前用户是否属于以下所有角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAllRoles(String ... roleNames) {
        if(roleNames == null || roleNames.length == 0){
            return false ;
        }
        boolean hasAllRole = getSubject().hasAllRoles(Lists.newArrayList(roleNames));
        return hasAllRole;
    }

    /**
     * 验证当前用户是否拥有指定权限
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        Subject subject = getSubject();
        if(subject == null){
            return false ;
        }
        if(StringUtils.isBlank(permission)){
            return false ;
        }
        return subject.isPermitted(permission);
    }


    /**
     * 验证当前用户是否拥有所有指定权限
     *
     * @param permissions 权限名 数组
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String ... permissions) {
        Subject subject = getSubject();
        if(subject == null){
            return false ;
        }
        if(permissions == null || permissions.length == 0){
            return false ;
        }
        return subject.isPermittedAll(permissions);
    }

}