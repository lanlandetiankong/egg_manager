package com.egg.manager.api.trait.routine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zhoucj
 * @description: 如果有不可用于dubbo的，迁移到此处
 * @date 2020/10/21
 */
@Component
public class RoutineCommonFunc {
    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    public <T> Page<T> parsePaginationToRowBounds(AntdvPaginationBean<T> paginationBean) {
        if (paginationBean != null) {
            Integer current = paginationBean.getCurrent();
            Integer pageSize = paginationBean.getPageSize();
            current = current != null ? current : 1;
            pageSize = pageSize != null ? pageSize : 0;
            int offset = (current - 1) * pageSize;
            return new Page<>(offset, pageSize);
        } else {
            return new Page<T>();
        }
    }


    /**
     * 将取得请求的header转化为 RequestHeaderBean
     * @param request
     */
    public RequestHeaderBean gainRequestHeaderBeanByRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject jsonObject = new JSONObject();
        if (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            jsonObject.put(headerName, request.getHeader(headerName));
        }
        return RequestHeaderBean.jsonObjectToBean(jsonObject);
    }


    /**
     * 取得springmvc 所有映射的请求 path路径
     * @param request
     * @return
     */
    public List<String> gainMvcMappingUrl(HttpServletRequest request) {
        //存储所有url集合
        List<String> uList = new ArrayList<String>();
        //获取上下文对象
        WebApplicationContext wac = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        //通过上下文对象获取RequestMappingHandlerMapping实例对象
        RequestMappingHandlerMapping bean = wac.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition prc = rmi.getPatternsCondition();
            Set<String> patterns = prc.getPatterns();
            for (String uStr : patterns) {
                uList.add(uStr);
            }
        }
        return uList;
    }


    /**
     * 将取得请求的token转化为 UserAccountToken
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountToken
     * @throws MyAuthenticationExpiredException
     * @throws IllegalAccessException
     */
    public UserAccountToken gainUserAccountTokenBeanByRequest(HttpServletRequest request, boolean isRequired) throws MyAuthenticationExpiredException {
        UserAccountToken accountToken = null;
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            //如果能取得 token
            accountToken = JSON.parseObject(token, UserAccountToken.class);
        }
        if (isRequired && accountToken == null) {
            //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException();
        }
        return accountToken;
    }
}
