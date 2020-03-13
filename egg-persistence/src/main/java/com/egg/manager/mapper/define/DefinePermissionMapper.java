package com.egg.manager.mapper.define;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.dto.define.DefineJobDto;
import com.egg.manager.dto.define.DefinePermissionDto;
import com.egg.manager.entity.define.DefinePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefinePermissionMapper extends BaseMapper<DefinePermission> {

    /**
     * [分页搜索查询] - 权限定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefinePermissionDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds,@Param("loginUser") UserAccount loginUser) ;

    /**
     * 删除指定角色id下的所有权限
     * @param roleId
     * @return
     */
    int clearAllPermissionByRoleId(@Param("roleId")String roleId,@Param("loginUser") UserAccount loginUser) ;
    /**
     * 取得角色拥有的所有权限集合
     * @param roleId
     * @return
     */
    List<DefinePermission> findAllPermissionByRoleId(String roleId) ;
    /**
     * 取得角色拥有的所有权限id集合
     * @param roleId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllPermissionIdByRoleId(@Param("roleId") String roleId,@Param("filterEnable")boolean filterEnable) ;


    /**
     * 查询 用户拥有的所有权限
     * @param userAccountId
     * @return
     */
    List<DefinePermission> findAllPermissionByUserAcccountId(@Param("userAccountId")String userAccountId) ;
}
