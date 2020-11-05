package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.enhance.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineMenuMapper extends MyEggMapper<DefineMenu> {

    /**
     * [分页搜索查询] - 菜单定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineMenuDto> selectQueryPage(Page<DefineMenuDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 取得角色拥有的所有[菜单]集合
     * @param roleId
     * @param state
     * @return
     */
    List<DefineMenu> findAllMenuByRoleId(@Param("roleId") Long roleId, @Param("stateVal") Short state);

    /**
     * 取得角色拥有的所有[菜单]集合(忽略 有子节点的菜单节点)
     * @param roleId
     * @param state
     * @return
     */
    List<DefineMenu> findAllMenuByRoleIdFilterParentNode(@Param("roleId") Long roleId, @Param("stateVal") Short state);


    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<DefineMenu> getUserGrantedMenusByAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId);

    /**
     * 查询菜单(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineMenu> getMenusFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

}
