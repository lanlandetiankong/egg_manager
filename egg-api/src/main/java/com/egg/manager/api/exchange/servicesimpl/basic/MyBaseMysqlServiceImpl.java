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
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.commons.util.reflex.EggReflexUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
        Integer count = baseMapper.deleteByIdWithModifyFill(delId, loginUser);
        return count;
    }

    @Override
    public Integer dealBatchLogicDelete(UserAccountEntity loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Lists.newArrayList(delIds);
            //批量伪删除
            delCount = baseMapper.batchDeleteByIdsWithModifyFill(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public QueryWrapper<T> doGetPageQueryWrapper(UserAccountEntity loginUser, WebResult result, List<QueryField> queryFieldList, AntdvPage vpage,
                                                 AntdvSortMap sortMap) {
        //解析 搜索条件
        QueryWrapper<T> entityWrapper = new QueryWrapper<T>();
        //调用方法将查询条件设置到userAccountEntityWrapper
        this.dealSetConditionsMapToEntityWrapper(entityWrapper, queryFieldList);
        //添加排序
        if (CollectionUtil.isNotEmpty(sortMap)) {
            for (String fieldKey : sortMap.keySet()) {
                entityWrapper.orderBy(true, sortMap.getVal(fieldKey), fieldKey);
            }
        }
        return entityWrapper;
    }


    @Override
    public void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, List<QueryField> queryFieldList) {
        if (CollectionUtil.isNotEmpty(queryFieldList)) {
            for (QueryField queryField : queryFieldList) {
                Object fieldValue = queryField.getValue();
                if (fieldValue == null) {
                    continue;
                } else {
                    if ("equals".equals(queryField.getMatching())) {
                        queryWrapper.eq(queryField.getFieldName(), fieldValue);
                    } else if ("like".equals(queryField.getMatching())) {
                        String fieldValueStr = String.valueOf(fieldValue);
                        queryWrapper.like(queryField.getFieldName(), fieldValueStr);
                    } else if ("notEquals".equals(queryField.getMatching())) {
                        queryWrapper.ne(queryField.getFieldName(), fieldValue);
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
            String userAccountId = userAccountToken.getUserAccountId();
            if (StringUtils.isNotBlank(userAccountId)) {
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
    public Page dealAntvPageToPagination(AntdvPage vpage) {
        Page pagination = new Page();
        if (vpage != null) {
            pagination.setCurrent(vpage.getCurrent());
            pagination.setSize(vpage.getPageSize());
        }
        return pagination;
    }

    @Override
    public boolean checkUserAccountIsBlank(UserAccountEntity userAccountEntity) {
        if (userAccountEntity == null || StringUtils.isBlank(userAccountEntity.getFid())) {
            return true;
        }
        return false;
    }


}
