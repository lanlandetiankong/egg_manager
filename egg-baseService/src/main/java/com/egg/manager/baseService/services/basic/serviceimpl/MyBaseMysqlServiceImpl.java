package com.egg.manager.baseService.services.basic.serviceimpl;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.constant.pojo.mysql.MyBaseMysqlEntityFieldConstant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import com.egg.manager.persistence.utils.reflex.EggReflexUtil;
import com.egg.manager.persistence.utils.reflex.config.EggPojoReflexFieldConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: MyBaseMysqlServiceImpl
 * @Author: zhoucj
 * @Date: 2020/9/18 9:21
 */
public class MyBaseMysqlServiceImpl<M extends BaseMapper<T>, T extends Model<T>, V extends MyBaseMysqlVo> extends ServiceImpl<M, T>
        implements MyBaseMysqlService<M, T, V> {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserAccountMapper userAccountMapper;


    @Override
    public EntityWrapper<T> doGetPageQueryWrapper(UserAccount loginUser, MyCommonResult<V> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                  List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<T> entityWrapper = new EntityWrapper<T>();
        //调用方法将查询条件设置到userAccountEntityWrapper
        this.dealSetConditionsMapToEntityWrapper(entityWrapper, queryFormFieldBeanList);
        //添加排序
        if (sortBeans != null && sortBeans.isEmpty() == false) {
            for (AntdvSortBean sortBean : sortBeans) {
                entityWrapper.orderBy(sortBean.getField(), sortBean.getOrderIsAsc());
            }
        }
        return entityWrapper;
    }


    @Override
    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList) {
        if (queryFieldBeanList != null && queryFieldBeanList.isEmpty() == false) {
            for (QueryFormFieldBean queryFormFieldBean : queryFieldBeanList) {
                Object fieldValue = queryFormFieldBean.getValue();
                if (fieldValue == null) {
                    continue;
                } else {
                    if ("equals".equals(queryFormFieldBean.getMatching())) {
                        entityWrapper.eq(queryFormFieldBean.getFieldName(), fieldValue);
                    } else if ("like".equals(queryFormFieldBean.getMatching())) {
                        String fieldValueStr = String.valueOf(fieldValue);
                        entityWrapper.like(queryFormFieldBean.getFieldName(), fieldValueStr);
                    } else if ("notEquals".equals(queryFormFieldBean.getMatching())) {
                        entityWrapper.ne(queryFormFieldBean.getFieldName(), fieldValue);
                    }
                }
            }
        }
    }


    @Override
    public T doBeforeCreate(UserAccount loginUser, T t, boolean uuidFlag) {
        Date now = new Date();
        if (uuidFlag) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID, MyUUIDUtil.renderSimpleUUID());
        }
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE, BaseStateEnum.ENABLED.getValue());
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.CREATE_TIME, now);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.UPDATE_TIME, now);
        if (loginUser != null) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.CREATE_USER_ID, loginUser.getFid());
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID, loginUser.getFid());
        }
        return t;
    }

    @Override
    public T doBeforeUpdate(UserAccount loginUser, T t) {
        Date now = new Date();
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE, BaseStateEnum.ENABLED.getValue());
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.UPDATE_TIME, now);
        if (loginUser != null) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID, loginUser.getFid());
        }
        return t;
    }


    @Override
    public T doBeforeDeleteOneById(UserAccount loginUser, Class<T> tClass, String idVal) {
        T t = EggReflexUtil.handlePojoGetInstance(tClass);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID, idVal);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE, BaseStateEnum.DELETE.getValue());
        if (loginUser != null) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID, loginUser.getFid());
        }
        return t;
    }


    @Override
    @Deprecated
    public <T, KV, KL> MyCommonResult doGetResultListSetToEntitySelect(MyCommonResult<T> result, EggPojoReflexFieldConfig<KV> valueConf, EggPojoReflexFieldConfig<KL> labelConf) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<T> resultList = result.getResultList();
        if (resultList != null && resultList.isEmpty() == false) {
            for (T t : resultList) {
                KV kv = EggReflexUtil.handlePojoGetFieldValue(t, valueConf);
                KL kl = EggReflexUtil.handlePojoGetFieldValue(t, labelConf);
                String kvStr = kv != null ? (String) kv : "";
                String klStr = kl != null ? (String) kl : "";
                enumList.add(new FrontEntitySelectBean(kvStr, klStr));
            }
        }
        result.setEnumList(enumList);
        return result;
    }

    /**
     * 用userAccountToken 取得 UserAccountXlsModel
     *
     * @param userAccountToken
     * @param isRequired       是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public UserAccount dealUserAccountTokenGetEntity(UserAccountToken userAccountToken, boolean isRequired) throws InvocationTargetException, IllegalAccessException {
        UserAccount userAccount = null;
        if (userAccountToken != null) {
            String userAccountId = userAccountToken.getUserAccountId();
            if (StringUtils.isNotBlank(userAccountId)) {
                userAccount = userAccountMapper.selectById(userAccountId);
            }
        }
        if (isRequired && userAccount == null) {  //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException();
        }
        return userAccount;
    }

    /**
     * 将取得请求的token转化为 UserAccountXlsModel
     * (已改用注解方式注入)
     *
     * @param request
     * @param isRequired 是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    //@Override
    @Deprecated
    public UserAccount gainUserAccountByRequest(HttpServletRequest request, boolean isRequired) throws MyAuthenticationExpiredException, InvocationTargetException, IllegalAccessException {
        UserAccount userAccount = null;
        UserAccountToken accountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, isRequired);
        if (accountToken != null) {
            userAccount = this.dealUserAccountTokenGetEntity(accountToken, isRequired);
        }
        if (isRequired && userAccount == null) {  //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException();
        }
        return userAccount;
    }

    @Override
    public Pagination dealAntvPageToPagination(AntdvPaginationBean paginationBean) {
        Pagination pagination = new Pagination();
        if (paginationBean != null) {
            pagination.setCurrent(paginationBean.getCurrent());
            pagination.setSize(paginationBean.getPageSize());
        }
        return pagination;
    }


}