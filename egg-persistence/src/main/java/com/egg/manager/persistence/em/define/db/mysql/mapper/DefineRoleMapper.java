package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineRoleMapper extends MyEggMapper<DefineRoleEntity> {
    /**
     * [分页搜索查询] - 角色定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<DefineRoleDto> selectQueryPage(Page<DefineRoleDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 查询指定用户的 用户-角色 关联表
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<DefineRoleEntity> findAllRoleByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("stateVal") Short stateVal);

}
