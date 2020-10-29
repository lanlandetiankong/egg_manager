package com.egg.manager.persistence.db.mysql.mapper.user;

import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserRoleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserRoleMapper extends MyEggMapper<UserRole> {
    /**
     * [分页搜索查询] - 用户角色
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserRoleDto> selectQueryPage(Page<UserRoleDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有角色id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllRoleIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable);

    /**
     * 批量新增 用户-角色 关联
     * @param roleList
     * @return
     */
    int customBatchInsert(List<UserRole> roleList);

    /**
     * 根据用户id 修改指定角色关联 的可用状态
     * @param userAccountId
     * @param roleIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("roleIdList") List<String> roleIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
