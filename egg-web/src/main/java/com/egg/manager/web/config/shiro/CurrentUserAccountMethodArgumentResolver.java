package com.egg.manager.web.config.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.services.redis.service.user.UserAccountRedisService;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.exception.MyUnauthorizedException;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * \* note: 增强方法注入，将含有 @CurrentLoginUser 注解的方法参数注入当前登录用户
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 16:51
 * \* Description:
 * \
 */
public class CurrentUserAccountMethodArgumentResolver implements HandlerMethodArgumentResolver{

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    @Reference
    private RedisHelper redisHelper ;
    @Reference
    private UserAccountRedisService userAccountRedisService ;

    /**
     * 判断:
     * 1、方法参数列表是否有 [UserAccountXlsModel]
     * 2、是否有 @CurrentLoginUser 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserAccount.class) && parameter.hasParameterAnnotation(CurrentLoginUser.class);
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
        UserAccount userAccount = (UserAccount) nativeWebRequest.getAttribute("currentLoginUser", RequestAttributes.SCOPE_REQUEST) ;
        if(userAccount == null){
            String authorization = nativeWebRequest.getHeader("authorization");
            if(StringUtils.isNotBlank(authorization)){
                userAccount = userAccountRedisService.dealGetCurrentLoginUserByAuthorization(authorization);
            }
        }
        CurrentLoginUser currentLoginUserAnno  = methodParameter.getParameterAnnotation(CurrentLoginUser.class);
        if(currentLoginUserAnno != null){
            boolean required = currentLoginUserAnno.required();
            if(userAccount == null && required){
                throw new MyUnauthorizedException("获取用户信息失败");
            }
        }
        return userAccount ;
    }
}
