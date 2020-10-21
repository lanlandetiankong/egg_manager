package com.egg.manager.persistence.db.mysql.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserTenantMapper extends BaseMapper<UserTenant> {
    /**
     * [分页搜索查询] - 用户&租户
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserTenantDto> selectQueryPage(Page<UserTenantDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有租户id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllTenantIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 批量新增 用户-租户 关联
     * @param tenantList
     * @return
     */
    int customBatchInsert(List<UserTenant> tenantList);

    /**
     * 根据用户id 修改指定租户关联 是否管理员 的状态
     * @param tenantId
     * @param userAccountIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateManagerUserStateByTenantId(@Param("tenantId") String tenantId, @Param("userAccountIdList") List<String> userAccountIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
