package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.em.user.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.dto.DefineTenantDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineTenantMapper extends MyEggMapper<DefineTenantEntity> {

    /**
     * [分页搜索查询] - 租户定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineTenantDto> selectQueryPage(Page<DefineTenantDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);


    /**
     * 根据用户id查询 所属的租户详情
     * @param userAccountId
     * @param tenantState
     * @return
     */
    DefineTenantEntity selectOneOfUserBelongTenant(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("tenantState") Short tenantState);

    /**
     * 根据用户id查询 所属的租户详情-dto
     * @param userAccountId
     * @return
     */
    DefineTenantDto selectOneDtoOfUserBelongTenant(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId);

    /**
     * 取得租户设置的所有管理员的用户id集合
     * @param tenantId     租户id
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<Long> findAllManagerUserIdByTenantId(@Param("tenantId") Long tenantId, @Param("filterEnable") boolean filterEnable);

    /**
     * 删除指定租户id下的所有管理员
     * @param tenantId
     * @param loginUser
     * @return
     */
    int clearAllManagerByTenantId(@Param("tenantId") Long tenantId, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);
}
