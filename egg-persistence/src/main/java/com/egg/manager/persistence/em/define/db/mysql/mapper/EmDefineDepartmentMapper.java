package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineDepartmentMapper extends MyEggMapper<EmDefineDepartmentEntity> {


    /**
     * [分页搜索查询] - 部门定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmDefineDepartmentDto> selectQueryPage(Page<EmDefineDepartmentDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 查询部门(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<EmDefineDepartmentEntity> getDepartmentFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @param departmentState
     * @return
     */
    EmDefineDepartmentEntity selectOneOfUserBelongDepartment(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("departmentState") Short departmentState);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @return
     */
    EmDefineDepartmentDto selectOneDtoOfUserBelongDepartment(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId);
}
