package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefinePermissionMapper extends MyEggMapper<DefinePermissionEntity> {

    /**
     * [分页搜索查询] - 权限定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefinePermissionDto> selectQueryPage(Page<DefinePermissionDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 批量 启用
     * @param ensureIds
     * @param loginUser
     * @return
     */
    int batchEnsureByIds(@Param("ensureIds") List<Long> ensureIds, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);

    /**
     * 删除指定角色id下的所有权限
     * @param roleId
     * @param loginUser
     * @return
     */
    int clearAllPermissionByRoleId(@Param("roleId") Long roleId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);

    /**
     * 取得角色拥有的所有权限集合
     * @param roleId
     * @return
     */
    List<DefinePermissionEntity> findAllPermissionByRoleId(Long roleId);

    /**
     * 取得角色拥有的所有权限id集合
     * @param roleId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<Long> findAllPermissionIdByRoleId(@Param("roleId") Long roleId, @Param("filterEnable") boolean filterEnable);


    /**
     * 查询 用户拥有的所有权限
     * @param userAccountId
     * @return
     */
    List<DefinePermissionEntity> findAllPermissionByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId);
}
