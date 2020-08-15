package com.egg.manager.api.service.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.webvo.session.UserAccountToken;
import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:54
 * \* Description:
 * \
 */
public interface CommonFuncService {

    Integer defaultVersion = 0 ;

    void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList);


    /**
     *  将取得请求的token转化为 UserAccountToken
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountToken
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    UserAccountToken gainUserAccountTokenBeanByRequest(HttpServletRequest request,boolean isRequired) throws InvocationTargetException, IllegalAccessException;

    /**
     *  将取得请求的header转化为 RequestHeaderBean
     * @param request
     */
    RequestHeaderBean gainRequestHeaderBeanByRequest(HttpServletRequest request) ;
    /**
     *  将取得请求的token转化为 UserAccountXlsModel
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountToken
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    //UserAccountXlsModel gainUserAccountByRequest(HttpServletRequest request,boolean isRequired) throws InvocationTargetException, IllegalAccessException ;


    /**
     *  用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    UserAccount dealUserAccountTokenGetEntity(UserAccountToken userAccountToken,boolean isRequired) throws InvocationTargetException, IllegalAccessException;


    /**
     * 取得springmvc 所有映射的请求 path路径
     * @param request
     * @return
     */
    List<String> gainMvcMappingUrl(HttpServletRequest request);



    /**
     * 取得 mybatisplus-分页查询Pagination
     * @param paginationBean 分页bean
     * @return
     */
    Pagination dealAntvPageToPagination(AntdvPaginationBean paginationBean);
}
