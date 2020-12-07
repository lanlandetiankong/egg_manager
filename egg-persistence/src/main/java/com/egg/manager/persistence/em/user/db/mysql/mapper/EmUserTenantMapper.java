package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserTenantDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserTenantMapper extends MyEggMapper<EmUserTenantEntity> {
    /**
     * [分页搜索查询] - 用户&租户
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmUserTenantDto> selectQueryPage(Page<EmUserTenantDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 取得用户拥有的所有租户id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllTenantIdByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 根据用户id 修改指定租户关联 是否管理员 的状态
     * @param tenantId
     * @param userAccountIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateManagerUserStateByTenantId(@Param("tenantId") String tenantId, @Param("userAccountIdList") List<String> userAccountIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);
}
