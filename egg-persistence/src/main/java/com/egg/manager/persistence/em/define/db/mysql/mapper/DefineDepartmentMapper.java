package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineDepartmentMapper extends MyEggMapper<DefineDepartmentEntity> {


    /**
     * [分页搜索查询] - 部门定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineDepartmentDto> selectQueryPage(Page<DefineDepartmentDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 查询部门(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineDepartmentEntity> getDepartmentFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @param departmentState
     * @return
     */
    DefineDepartmentEntity selectOneOfUserBelongDepartment(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("departmentState") Short departmentState);

    /**
     * 根据用户id查询 所属的部门详情-dto
     * @param userAccountId
     * @return
     */
    DefineDepartmentDto selectOneDtoOfUserBelongDepartment(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId);
}
