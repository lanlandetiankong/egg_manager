package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefinePermissionDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefinePermissionMapper extends MyEggMapper<EmDefinePermissionEntity> {

    /**
     * [分页搜索查询] - 权限定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmDefinePermissionDto> selectQueryPage(Page<EmDefinePermissionDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 批量 启用
     * @param ensureIds
     * @param loginUser
     * @return
     */
    int batchEnsureByIds(@Param("ensureIds") List<String> ensureIds, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);

    /**
     * 删除指定角色id下的所有权限
     * @param roleId
     * @param loginUser
     * @return
     */
    int clearAllPermissionByRoleId(@Param("roleId") String roleId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);

    /**
     * 取得角色拥有的所有权限集合
     * @param roleId
     * @return
     */
    List<EmDefinePermissionEntity> findAllPermissionByRoleId(String roleId);

    /**
     * 取得角色拥有的所有权限id集合
     * @param roleId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllPermissionIdByRoleId(@Param("roleId") String roleId, @Param("filterEnable") boolean filterEnable);


    /**
     * 查询 用户拥有的所有权限
     * @param userAccountId
     * @return
     */
    List<EmDefinePermissionEntity> findAllPermissionByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId);

}
