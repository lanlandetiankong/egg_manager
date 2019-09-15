package com.egg.manager.config.shiro;

import com.egg.manager.annotation.log.CurrentUserAccount;
import com.egg.manager.entity.user.UserAccount;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * \* note: 增强方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 16:51
 * \* Description:
 * \
 */
public class CurrentUserAccountMethodArgumentResolver implements HandlerMethodArgumentResolver{

    /**
     * 判断1、方法参数列表是否有 [UserAccount]
     * 2、是否有 @CurrentUser 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserAccount.class) && parameter.hasParameterAnnotation(CurrentUserAccount.class);
    }

    /**
     * 取得用户并返回注入，取得用户失败将会抛出UnauthorizedException异常
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        UserAccount userAccount = (UserAccount) nativeWebRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST) ;
        if(userAccount == null){
            throw new UnauthorizedException("获取用户信息失败");
        }
        return userAccount ;
    }
}
