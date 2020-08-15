package com.egg.manager.persistence.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.dto.user.UserTenantDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.entity.user.UserTenant;
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
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserTenantDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有租户id集合
     *
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllTenantIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 批量新增 用户-租户 关联
     *
     * @param tenantList
     * @return
     */
    int customBatchInsert(List<UserTenant> tenantList);

    /**
     * 根据用户id 修改指定租户关联 的可用状态
     *
     * @param userAccountId
     * @param tenantIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("tenantIdList") List<String> tenantIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
