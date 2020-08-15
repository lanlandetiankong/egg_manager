package com.egg.manager.service.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.api.service.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.api.service.redis.service.RedisHelper;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.webvo.session.UserAccountToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Service(interfaceClass = CommonFuncService.class)
public class CommonFuncServiceImpl implements CommonFuncService {

    @Autowired
    private UserAccountMapper userAccountMapper ;

    @Reference
    private RedisHelper redisHelper ;

    @Override
    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList){
        if(queryFieldBeanList != null && queryFieldBeanList.isEmpty() == false){
            for(QueryFormFieldBean queryFormFieldBean : queryFieldBeanList){
                Object fieldValue = queryFormFieldBean.getValue();
                if(fieldValue == null){
                    continue;
                }   else {
                    if("equals".equals(queryFormFieldBean.getMatching())){
                        entityWrapper.eq(queryFormFieldBean.getFieldName(),fieldValue) ;
                    }   else if("like".equals(queryFormFieldBean.getMatching())){
                        String fieldValueStr = String.valueOf(fieldValue) ;
                        entityWrapper.like(queryFormFieldBean.getFieldName(),fieldValueStr) ;
                    }   else if("notEquals".equals(queryFormFieldBean.getMatching())){
                        entityWrapper.ne(queryFormFieldBean.getFieldName(),fieldValue) ;
                    }
                }
            }
        }
    }





    /**
     *  将取得请求的token转化为 UserAccountToken
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountToken
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public UserAccountToken gainUserAccountTokenBeanByRequest(HttpServletRequest request,boolean isRequired) throws MyAuthenticationExpiredException {
        UserAccountToken accountToken = null ;
        String token = request.getHeader("token");
        if(StringUtils.isNotBlank(token)){  //如果能取得 token
            accountToken = JSON.parseObject(token,UserAccountToken.class);
        }
        if(isRequired && accountToken == null){ //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException() ;
        }
        return accountToken ;
    }


    /**
     *  将取得请求的header转化为 RequestHeaderBean
     * @param request
     */
    @Override
    public RequestHeaderBean gainRequestHeaderBeanByRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject jsonObject = new JSONObject() ;
        if(headerNames.hasMoreElements()){
            String headerName  = headerNames.nextElement() ;
            jsonObject.put(headerName,request.getHeader(headerName));
        }
        return RequestHeaderBean.jsonObjectToBean(jsonObject);
    }


    /**
     *  将取得请求的token转化为 UserAccountXlsModel
     *  (已改用注解方式注入)
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    //@Override
    @Deprecated
    public UserAccount gainUserAccountByRequest(HttpServletRequest request,boolean isRequired) throws MyAuthenticationExpiredException{
        UserAccount userAccount = null ;
        UserAccountToken accountToken = this.gainUserAccountTokenBeanByRequest(request,isRequired);
        if(accountToken != null) {
            userAccount = this.dealUserAccountTokenGetEntity(accountToken,isRequired);
        }
        if(isRequired && userAccount == null){  //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException() ;
        }
        return userAccount ;
    }

    /**
     *  用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public UserAccount dealUserAccountTokenGetEntity(UserAccountToken userAccountToken,boolean isRequired) throws MyAuthenticationExpiredException{
        UserAccount userAccount = null ;
        if(userAccountToken != null){
            String userAccountId = userAccountToken.getUserAccountId() ;
            if(StringUtils.isNotBlank(userAccountId)){
                userAccount = userAccountMapper.selectById(userAccountId);
            }
        }
        if(isRequired && userAccount == null){  //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException() ;
        }
        return userAccount ;
    }


    /**
     * 取得springmvc 所有映射的请求 path路径
     * @param request
     * @return
     */
    @Override
    public List<String> gainMvcMappingUrl(HttpServletRequest request){
        List<String> uList = new ArrayList<String>();//存储所有url集合
        WebApplicationContext wac = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);//获取上下文对象
        RequestMappingHandlerMapping bean = wac.getBean(RequestMappingHandlerMapping.class);//通过上下文对象获取RequestMappingHandlerMapping实例对象
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
     * 取得 mybatisplus-分页查询Pagination
     * @param paginationBean
     * @return
     */
    @Override
    public Pagination dealAntvPageToPagination(AntdvPaginationBean paginationBean){
        Pagination pagination = new Pagination();
        if(paginationBean != null){
            pagination.setCurrent(paginationBean.getCurrent());
            pagination.setSize(paginationBean.getPageSize());
        }
        return pagination ;
    }





}
