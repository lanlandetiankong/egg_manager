package com.egg.manager.web.config.resolver;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.redis.service.user.UserAccountRedisService;
import com.egg.manager.common.annotation.user.CurrentLoginerBelongTenant;
import com.egg.manager.common.exception.MyUnauthorizedException;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * \* note: 增强方法注入，将含有 @CurrentLoginerBelongTenant 注解的方法参数注入当前登录用户
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 16:51
 * \* Description:
 * \
 */
public class CurrentUserBlongTenantMethodArgumentResolver implements HandlerMethodArgumentResolver{

    @Reference
    private UserAccountRedisService userAccountRedisService ;

    /**
     * 判断:
     * 1、方法参数列表是否有 [DefineTenant]参数
     * 2、是否有 @CurrentLoginerBelongTenant 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(DefineTenant.class) && parameter.hasParameterAnnotation(CurrentLoginerBelongTenant.class);
    }

    /**
     * 取得用户并返回注入，取得租户信息失败将会抛出UnauthorizedException异常
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        DefineTenant defineTenant = (DefineTenant) nativeWebRequest.getAttribute("currentLoginUser", RequestAttributes.SCOPE_REQUEST) ;
        if(defineTenant == null){
            String authorization = nativeWebRequest.getHeader("authorization");
            if(StringUtils.isNotBlank(authorization)){
                defineTenant = userAccountRedisService.dealGetCurrentLoginerBelongTenantByAuthorization(authorization);
            }
        }
        CurrentLoginerBelongTenant currentLoginerBelongTenantAnno  = methodParameter.getParameterAnnotation(CurrentLoginerBelongTenant.class);
        if(currentLoginerBelongTenantAnno != null){
            boolean required = currentLoginerBelongTenantAnno.required();
            if(defineTenant == null && required){
                throw new MyUnauthorizedException("获取所属租户信息失败");
            }
        }
        return defineTenant ;
    }
}
