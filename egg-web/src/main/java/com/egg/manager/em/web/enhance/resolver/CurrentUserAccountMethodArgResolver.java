package com.egg.manager.em.web.enhance.resolver;

import org.apache.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.enums.db.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.exception.MyUnauthorizedException;
import com.egg.manager.persistence.commons.util.basic.dubbo.DubboUtils;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author zhoucj
 * @description增强方法注入，将含有 @CurrentLoginUser 注解的方法参数注入当前登录用户
 * @date 2020/10/21
 */
public class CurrentUserAccountMethodArgResolver implements HandlerMethodArgumentResolver {

    @Reference
    private EmUserAccountService emUserAccountService;
    @Autowired
    private RedisHelper redisHelper;

    /**
     * 由于引用不到dubbo，所以
     */
    public void initReferBeans(){
        emUserAccountService = (emUserAccountService != null) ? emUserAccountService : DubboUtils.getRefBean(EmUserAccountService.class);
    }

    /**
     * 判断:
     * 1、方法参数列表是否有 [CurrentLoginEmUserInfo]
     * 2、是否有 @CurrentLoginUser 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentLoginEmUserInfo.class) && parameter.hasParameterAnnotation(CurrentLoginUser.class);
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
        this.initReferBeans();
        CurrentLoginEmUserInfo loginUserInfo = (CurrentLoginEmUserInfo) nativeWebRequest.getAttribute("currentLoginUser", RequestAttributes.SCOPE_REQUEST);
        if (loginUserInfo == null) {
            String authorization = nativeWebRequest.getHeader("authorization");
            if (StringUtils.isNotBlank(authorization)) {
                Object userTokenObj = redisHelper.hashGet(RedisShiroCacheEnum.authorization.getKey(), authorization);
                if (userTokenObj != null) {
                    String userTokenStr = (String) userTokenObj;
                    if (StringUtils.isNotBlank(userTokenStr)) {
                        UserAccountToken userToken = JSONObject.parseObject(userTokenStr, UserAccountToken.class);
                        loginUserInfo = emUserAccountService.queryDbToCacheable(userToken.getUserAccountId());
                    }
                }
            }
        }
        CurrentLoginUser currentLoginUserAnno = methodParameter.getParameterAnnotation(CurrentLoginUser.class);
        if (currentLoginUserAnno != null) {
            boolean required = currentLoginUserAnno.required();
            if (loginUserInfo == null && required) {
                throw new MyUnauthorizedException(BaseRstMsgConstant.ErrorMsg.userIsLoingOut());
            }
        }
        return loginUserInfo;
    }
}
