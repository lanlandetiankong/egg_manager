package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineRoleMapper extends MyEggMapper<DefineRole> {
    /**
     * [分页搜索查询] - 角色定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineRoleDto> selectQueryPage(Page<DefineRoleDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 查询指定用户的 用户-角色 关联表
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<DefineRole> findAllRoleByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("stateVal") Short stateVal);

}
