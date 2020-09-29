package com.egg.manager.baseservice.services.basic.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
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
    public QueryWrapper<T> doGetPageQueryWrapper(UserAccount loginUser, MyCommonResult<V> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                 List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<T> entityWrapper = new QueryWrapper<T>();
        //调用方法将查询条件设置到userAccountEntityWrapper
        this.dealSetConditionsMapToEntityWrapper(entityWrapper, queryFormFieldBeanList);
        //添加排序
        if (sortBeans != null && sortBeans.isEmpty() == false) {
            for (AntdvSortBean sortBean : sortBeans) {
                entityWrapper.orderBy(true, sortBean.getOrderIsAsc(),sortBean.getField());
            }
        }
        return entityWrapper;
    }


    @Override
    public void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, List<QueryFormFieldBean> queryFieldBeanList) {
        if (queryFieldBeanList != null && queryFieldBeanList.isEmpty() == false) {
            for (QueryFormFieldBean queryFormFieldBean : queryFieldBeanList) {
                Object fieldValue = queryFormFieldBean.getValue();
                if (fieldValue == null) {
                    continue;
                } else {
                    if ("equals".equals(queryFormFieldBean.getMatching())) {
                        queryWrapper.eq(queryFormFieldBean.getFieldName(), fieldValue);
                    } else if ("like".equals(queryFormFieldBean.getMatching())) {
                        String fieldValueStr = String.valueOf(fieldValue);
                        queryWrapper.like(queryFormFieldBean.getFieldName(), fieldValueStr);
                    } else if ("notEquals".equals(queryFormFieldBean.getMatching())) {
                        queryWrapper.ne(queryFormFieldBean.getFieldName(), fieldValue);
                    }
                }
            }
        }
    }


    @Override
    public T doBeforeCreate(UserAccount loginUser, T t, boolean uuidFlag) {
        Date now = new Date();
        if (uuidFlag) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID, MyUUIDUtil.renderSimpleUuid());
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
        if (isRequired && userAccount == null) {
            //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException();
        }
        return userAccount;
    }

    @Override
    public Page dealAntvPageToPagination(AntdvPaginationBean paginationBean) {
        Page pagination = new Page();
        if (paginationBean != null) {
            pagination.setCurrent(paginationBean.getCurrent());
            pagination.setSize(paginationBean.getPageSize());
        }
        return pagination;
    }

    @Override
    public boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if (userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true;
        }
        return false;
    }

}
