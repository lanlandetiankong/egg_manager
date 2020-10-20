package com.egg.manager.persistence.db.mysql.mapper.organization;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 租户表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie
 * @since 2019-09-12
 */
public interface DefineTenantMapper extends BaseMapper<DefineTenant> {

    /**
     * [分页搜索查询] - 租户定义
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineTenantDto> selectQueryPage(Page<DefineTenantDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);


    /**
     * 根据用户id查询 所属的租户详情
     *
     * @param userAccountId
     * @param tenantState
     * @return
     */
    DefineTenant selectOneOfUserBelongTenant(@Param("userAccountId") String userAccountId, @Param("tenantState") Short tenantState);

    /**
     * 根据用户id查询 所属的租户详情-dto
     *
     * @param userAccountId
     * @return
     */
    DefineTenantDto selectOneDtoOfUserBelongTenant(@Param("userAccountId") String userAccountId);

    /**
     * 取得租户设置的所有管理员的用户id集合
     *
     * @param tenantId 租户id
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllManagerUserIdByTenantId(@Param("tenantId") String tenantId, @Param("filterEnable") boolean filterEnable);
    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);

    /**
     * 删除指定租户id下的所有管理员
     * @param tenantId
     * @param loginUser
     * @return
     */
    int clearAllManagerByTenantId(@Param("tenantId") String tenantId, @Param("loginUser") UserAccount loginUser);
}
