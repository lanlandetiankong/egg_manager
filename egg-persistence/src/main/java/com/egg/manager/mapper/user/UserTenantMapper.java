package com.egg.manager.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.dto.user.UserTenantDto;
import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserTenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户&租户 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 */
public interface UserTenantMapper extends BaseMapper<UserTenant> {
    /**
     * [分页搜索查询] - 用户&租户
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserTenantDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有租户id集合
     * @param userAccountId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllTenantIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable) ;


    /**
     * 根据用户id 查询 所属的 租户定义
     * @param userAccountId 用户id
     * @param tenantState 租户状态,为null时不过滤
     * @return
     */
    DefineTenant selectOneOfUserBelongTenant(@Param("userAccountId")String userAccountId,@Param("tenantState")Integer tenantState);

    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;



    /**
     * 批量新增 用户-租户 关联
     * @param tenantList
     * @return
     */
    int customBatchInsert(List<UserTenant> tenantList);

    /**
     * 根据用户id 修改指定租户关联 的可用状态
     * @param userAccountId
     * @param tenantIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("tenantIdList") List<String> tenantIdList, @Param("stateVal") Integer stateVal
            , @Param("loginUser") UserAccount loginUser);
}