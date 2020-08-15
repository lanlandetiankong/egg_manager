package com.egg.manager.persistence.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.dto.define.DefineRoleDto;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色定义表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineRoleMapper extends BaseMapper<DefineRole> {
    /**
     * [分页搜索查询] - 角色定义
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineRoleDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 查询指定用户的 用户-角色 关联表
     *
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<DefineRole> findAllRoleByUserAcccountId(@Param("userAccountId") String userAccountId, @Param("stateVal") Short stateVal);

    /**
     * 批量 伪删除
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


}
