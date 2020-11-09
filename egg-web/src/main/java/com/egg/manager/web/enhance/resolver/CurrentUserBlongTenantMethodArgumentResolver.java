package com.egg.manager.web.enhance.resolver;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.user.redis.UserAccountRedisService;
import com.egg.manager.persistence.em.user.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginerBelongTenant;
import com.egg.manager.persistence.commons.base.exception.MyUnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author zhoucj
 * @description 增强方法注入，将含有 @CurrentLoginerBelongTenant 注解的方法参数注入当前登录用户
 * @date 2020/10/21
 */
public class CurrentUserBlongTenantMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Reference
    private UserAccountRedisService userAccountRedisService;

    /**
     * 判断:
     * 1、方法参数列表是否有 [DefineTenant]参数
     * 2、是否有 @CurrentLoginerBelongTenant 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(DefineTenantEntity.class) && parameter.hasParameterAnnotation(CurrentLoginerBelongTenant.class);
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
        DefineTenantEntity defineTenantEntity = (DefineTenantEntity) nativeWebRequest.getAttribute("currentLoginerBelongTenant", RequestAttributes.SCOPE_REQUEST);
        if (defineTenantEntity == null) {
            String authorization = nativeWebRequest.getHeader("authorization");
            if (StringUtils.isNotBlank(authorization)) {
                defineTenantEntity = userAccountRedisService.dealGetCurrentLoginerBelongTenantByAuthorization(null, authorization);
            }
        }
        CurrentLoginerBelongTenant currentLoginerBelongTenantAnno = methodParameter.getParameterAnnotation(CurrentLoginerBelongTenant.class);
        if (currentLoginerBelongTenantAnno != null) {
            boolean required = currentLoginerBelongTenantAnno.required();
            if (defineTenantEntity == null && required) {
                throw new MyUnauthorizedException("获取所属租户信息失败");
            }
        }
        return defineTenantEntity;
    }
}
