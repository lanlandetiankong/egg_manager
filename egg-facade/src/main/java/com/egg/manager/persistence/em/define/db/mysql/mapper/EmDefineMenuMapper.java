package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineMenuMapper extends MyEggMapper<EmDefineMenuEntity> {

    /**
     * [分页搜索查询] - 菜单定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmDefineMenuDto> selectQueryPage(Page<EmDefineMenuDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 取得角色拥有的所有[菜单]集合
     * @param roleId
     * @param state
     * @return
     */
    List<EmDefineMenuEntity> findAllMenuByRoleId(@Param("roleId") String roleId, @Param("stateVal") Short state);

    /**
     * 取得角色拥有的所有[菜单]集合(忽略 有子节点的菜单节点)
     * @param roleId
     * @param state
     * @return
     */
    List<EmDefineMenuEntity> findAllMenuByRoleIdFilterParentNode(@Param("roleId") String roleId, @Param("stateVal") Short state);


    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<EmDefineMenuEntity> getUserGrantedMenusByAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId);

    /**
     * 查询菜单(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<EmDefineMenuEntity> getMenusFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

}
