package com.egg.manager.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import com.egg.manager.webvo.session.UserAccountToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Service
public class CommonFuncServiceImpl implements CommonFuncService {

    @Autowired
    private UserAccountMapper userAccountMapper ;

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
                    }
                }
            }
        }
    }


    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    @Override
    public  RowBounds parsePaginationToRowBounds(AntdvPaginationBean paginationBean) {
        if(paginationBean != null){
            Integer current = paginationBean.getCurrent();
            Integer pageSize = paginationBean.getPageSize();
            current = current != null ? current : 1;
            pageSize = pageSize != null ? pageSize : 0;
            int offset = (current - 1) * pageSize ;
            return new RowBounds(offset,pageSize) ;
        }   else {
            return new RowBounds() ;
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
     *  将取得请求的token转化为 UserAccount
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccount
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
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
     *  用userAccountToken 取得 UserAccount
     * @param userAccountToken
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccount
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
}
