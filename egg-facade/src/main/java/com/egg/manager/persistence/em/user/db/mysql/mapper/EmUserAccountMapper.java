package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserAccountMapper extends MyEggMapper<EmUserAccountEntity> {

    /**
     * [通用查询] 根据用户id查询用户entity
     * @return
     */
    EmUserAccountEntity commonSelectUserAccountById();


    /**
     * 分页搜索查询
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @param queryTenantFieldBeanList
     * @param queryDepartmentFieldBeanList
     * @return
     */
    List<EmUserAccountDto> selectQueryPage(Page<EmUserAccountDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr,
                                           @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap,
                                           @Param("queryTenantFieldBeanList") QueryFieldArr queryTenantFieldBeanList,
                                           @Param("queryDepartmentFieldBeanList") QueryFieldArr queryDepartmentFieldBeanList
    );

    /**
     * 批量 锁定用户
     * @param lockIds
     * @param lockState
     * @param loginUser
     * @return
     */
    int batchLockUserByIds(@Param("lockIds") List<String> lockIds, @Param("lockState") int lockState, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);

    /**
     * 批量逻辑删除 指定用户的所有角色关联
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllRoleByUserId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);

    /**
     * 批量逻辑删除 指定用户的所有职务关联
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllJobByUserId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);
}
