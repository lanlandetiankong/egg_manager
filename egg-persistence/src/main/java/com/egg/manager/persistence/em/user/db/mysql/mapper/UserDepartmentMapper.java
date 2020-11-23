package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartmentEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserDepartmentDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserDepartmentMapper extends MyEggMapper<UserDepartmentEntity> {
    /**
     * [分页搜索查询] - 用户&部门
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<UserDepartmentDto> selectQueryPage(Page<UserDepartmentDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 取得用户拥有的所有部门id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllIdByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("filterEnable") boolean filterEnable);

    /**
     * 根据用户id 修改指定部门关联 的可用状态
     * @param userAccountId
     * @param departmentIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("departmentIdList") List<String> departmentIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);
}
