package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenant;
import com.egg.manager.persistence.em.user.pojo.dto.UserTenantDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserTenantMapper extends MyEggMapper<UserTenant> {
    /**
     * [分页搜索查询] - 用户&租户
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserTenantDto> selectQueryPage(Page<UserTenantDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有租户id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllTenantIdByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("filterEnable") boolean filterEnable);

    /**
     * 批量新增 用户-租户 关联
     * @param tenantList
     * @return
     */
    int customBatchInsert(List<UserTenant> tenantList);

    /**
     * 根据用户id 修改指定租户关联 是否管理员 的状态
     * @param tenantId
     * @param userAccountIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateManagerUserStateByTenantId(@Param("tenantId") Long tenantId, @Param("userAccountIdList") List<Long> userAccountIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);
}
