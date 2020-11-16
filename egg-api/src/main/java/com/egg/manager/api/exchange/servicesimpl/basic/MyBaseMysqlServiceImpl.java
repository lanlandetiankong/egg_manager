package com.egg.manager.api.exchange.servicesimpl.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.MyBaseMysqlEntityFieldConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.commons.util.reflex.EggReflexUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class MyBaseMysqlServiceImpl<M extends MyEggMapper<T>, T extends Model<T>, V extends MyBaseMysqlVo> extends ServiceImpl<M, T>
        implements MyBaseMysqlService<T, M, V> {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public Integer dealLogicDeleteById(UserAccountEntity loginUser, String delId) throws Exception {
        Integer count = baseMapper.deleteByIdWithModifyFill(delId,loginUser);
        return count;
    }

    @Override
    public Integer dealBatchLogicDelete(UserAccountEntity loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Lists.newArrayList(delIds);
            //批量伪删除
            delCount = baseMapper.batchDeleteByIdsWithModifyFill(delIdList,loginUser);
        }
        return delCount;
    }

    @Override
    public QueryWrapper<T> doGetPageQueryWrapper(UserAccountEntity loginUser, WebResult result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                 List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<T> entityWrapper = new QueryWrapper<T>();
        //调用方法将查询条件设置到userAccountEntityWrapper
        this.dealSetConditionsMapToEntityWrapper(entityWrapper, queryFormFieldBeanList);
        //添加排序
        if (CollectionUtil.isNotEmpty(sortBeans)) {
            for (AntdvSortBean sortBean : sortBeans) {
                entityWrapper.orderBy(true, sortBean.getOrderIsAsc(), sortBean.getField());
            }
        }
        return entityWrapper;
    }


    @Override
    public void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, List<QueryFormFieldBean> queryFieldBeanList) {
        if (CollectionUtil.isNotEmpty(queryFieldBeanList)) {
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
    public T doBeforeCreate(UserAccountEntity loginUser, T t) {
        Date now = new Date();
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
    public T doBeforeUpdate(UserAccountEntity loginUser, T t) {
        Date now = new Date();
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE, BaseStateEnum.ENABLED.getValue());
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.UPDATE_TIME, now);
        if (loginUser != null) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID, loginUser.getFid());
        }
        return t;
    }


    @Override
    public T doBeforeDeleteOneById(UserAccountEntity loginUser, Class<T> tClass, String idVal) {
        T t = EggReflexUtil.handlePojoGetInstance(tClass);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID, idVal);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.IS_DELETED, SwitchStateEnum.Open.getValue());
        if (loginUser != null) {
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID, loginUser.getFid());
        }
        return t;
    }


    /**
     * 用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired       是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public UserAccountEntity dealUserAccountTokenGetEntity(UserAccountToken userAccountToken, boolean isRequired) throws InvocationTargetException, IllegalAccessException {
        UserAccountEntity userAccountEntity = null;
        if (userAccountToken != null) {
            Long userAccountId = userAccountToken.getUserAccountId();
            if (LongUtils.isNotBlank(userAccountId)) {
                userAccountEntity = userAccountMapper.selectById(userAccountId);
            }
        }
        if (isRequired && userAccountEntity == null) {
            //强制取得用户身份认证，不存在时抛出异常
            throw new MyAuthenticationExpiredException();
        }
        return userAccountEntity;
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
    public boolean checkUserAccountIsBlank(UserAccountEntity userAccountEntity) {
        if (userAccountEntity == null || LongUtils.isBlank(userAccountEntity.getFid())) {
            return true;
        }
        return false;
    }



}
