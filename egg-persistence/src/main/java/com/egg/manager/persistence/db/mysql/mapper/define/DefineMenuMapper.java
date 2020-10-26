package com.egg.manager.persistence.db.mysql.mapper.define;

import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineMenuDto;
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
    List<DefineMenuDto> selectQueryPage(Page<DefineMenuDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得角色拥有的所有[菜单]集合
     * @param roleId
     * @param state
     * @return
     */
    List<DefineMenu> findAllMenuByRoleId(@Param("roleId") String roleId, @Param("stateVal") Short state);

    /**
     * 取得角色拥有的所有[菜单]集合(忽略 有子节点的菜单节点)
     * @param roleId
     * @param state
     * @return
     */
    List<DefineMenu> findAllMenuByRoleIdFilterParentNode(@Param("roleId") String roleId, @Param("stateVal") Short state);


    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<DefineMenu> getUserGrantedMenusByAccountId(@Param("userAccountId") String userAccountId);

    /**
     * 查询菜单(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineMenu> getMenusFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


}
