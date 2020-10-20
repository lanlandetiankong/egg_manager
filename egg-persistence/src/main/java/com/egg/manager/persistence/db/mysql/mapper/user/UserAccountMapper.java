package com.egg.manager.persistence.db.mysql.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie
 * @since 2019-09-12
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * [通用查询] 根据用户id查询用户entity
     *
     * @return
     */
    UserAccount commonSelectUserAccountById();


    /**
     * 分页搜索查询
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @param queryTenantFieldBeanList
     * @param queryDepartmentFieldBeanList
     * @return
     */
    List<UserAccountDto> selectQueryPage(Page<UserAccountDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList,
                                         @Param("sortFieldList") List<AntdvSortBean> sortBeans,
                                         @Param("queryTenantFieldBeanList") List<QueryFormFieldBean> queryTenantFieldBeanList,
                                         @Param("queryDepartmentFieldBeanList") List<QueryFormFieldBean> queryDepartmentFieldBeanList
    );


    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);

    /**
     * 批量 锁定用户
     *
     * @param lockIds
     * @param lockState
     * @param loginUser
     * @return
     */
    int batchLockUserByIds(@Param("lockIds") List<String> lockIds, @Param("lockState") int lockState, @Param("loginUser") UserAccount loginUser);

    /**
     * 批量伪删除 指定用户的所有角色关联
     *
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllRoleByUserId(@Param("userAccountId") String userAccountId, @Param("loginUser") UserAccount loginUser);

    /**
     * 批量伪删除 指定用户的所有职务关联
     *
     * @param userAccountId
     * @param loginUser
     * @return
     */
    int clearAllJobByUserId(@Param("userAccountId") String userAccountId, @Param("loginUser") UserAccount loginUser);
}
