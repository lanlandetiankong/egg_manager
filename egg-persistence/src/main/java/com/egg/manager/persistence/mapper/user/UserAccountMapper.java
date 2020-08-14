package com.egg.manager.persistence.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.dto.user.UserAccountDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * [通用查询] 根据用户id查询用户entity
     * @return
     */
    UserAccount commonSelectUserAccountById() ;



    /**
     * [分页搜索查询] - 用户
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @param sortBeans
     * @return
     */
    List<UserAccountDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList,
                                         @Param("sortFieldList") List<AntdvSortBean> sortBeans,
                                         @Param("queryTenantFieldBeanList") List<QueryFormFieldBean> queryTenantFieldBeanList
                                );


    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds,@Param("loginUser")UserAccount loginUser) ;

    /**
     * 批量 锁定用户
     * @param lockIds
     * @param lockState
     * @param loginUser
     * @return
     */
    int batchLockUserByIds(@Param("lockIds")List<String> lockIds,@Param("lockState") int lockState,@Param("loginUser")UserAccount loginUser) ;

    /**
     * 批量伪删除 指定用户的所有角色关联
     * @param userAccountId
     * @return
     */
    int clearAllRoleByUserId(@Param("userAccountId") String userAccountId,@Param("loginUser") UserAccount loginUser) ;

    /**
     * 批量伪删除 指定用户的所有职务关联
     * @param userAccountId
     * @return
     */
    int clearAllJobByUserId(@Param("userAccountId")String userAccountId,@Param("loginUser") UserAccount loginUser) ;
}
