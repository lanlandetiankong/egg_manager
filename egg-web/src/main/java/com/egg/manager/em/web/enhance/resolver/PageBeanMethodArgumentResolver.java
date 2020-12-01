package com.egg.manager.em.web.enhance.resolver;

import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.commons.util.page.PageUtil;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description增强方法注入，将含有 @QueryPage 分页信息
 * @date 2020/10/21
 */
@Slf4j
public class PageBeanMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断:
     * 1、方法参数列表是否有 [QueryPageBean]
     * 2、是否有 @QueryPage 注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(QueryPageBean.class) && parameter.hasParameterAnnotation(QueryPage.class);
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
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //方法的参数注解
        QueryPage queryPageAnno = methodParameter.getParameterAnnotation(QueryPage.class);
        //解析json转为bean
        AntdvPage antdvPage = PageUtil.parsePaginationJsonToBean(httpServletRequest, queryPageAnno.tClass());
        QueryFieldArr queryFields = PageUtil.parseQueryJsonToBeanList(httpServletRequest);
        AntdvSortMap antdvSortMap = PageUtil.parseSortJsonToBean(httpServletRequest, queryPageAnno.withCreateTimeDesc());
        QueryPageBean queryPageBean = new QueryPageBean(antdvPage, queryFields, antdvSortMap).nullToInit();
        return queryPageBean;
    }
}
