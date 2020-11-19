package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserAccountMapper extends MyEggMapper<UserAccountEntity> {

    /**
     * [通用查询] 根据用户id查询用户entity
     * @return
     */
    UserAccountEntity commonSelectUserAccountById();


    /**
     * 分页搜索查询
     * @param page
     * @param queryFieldList
     * @param sortMap
     * @param queryTenantFieldBeanList
     * @param queryDepartmentFieldBeanList
     * @return
     */
    List<UserAccountDto> selectQueryPage(Page<UserAccountDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryField> queryFieldList,
                                         @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap,
                                         @Param("queryTenantFieldBeanList") List<QueryField> queryTenantFieldBeanList,
                                         @Param("queryDepartmentFieldBeanList") List<QueryField> queryDepartmentFieldBeanList
    );

    /**
     * 批量 锁定用户
     * @param lockIds
     * @param lockState
     * @param loginUser
     * @return
     */
    int batchLockUserByIds(@Param("lockIds") List<String> lockIds, @Param("lockState") int lockState, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);

    /**
     * 批量伪删除 指定用户的所有角色关联
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllRoleByUserId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);

    /**
     * 批量伪删除 指定用户的所有职务关联
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllJobByUserId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);
}
