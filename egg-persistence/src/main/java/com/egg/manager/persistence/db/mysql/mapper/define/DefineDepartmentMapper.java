package com.egg.manager.persistence.db.mysql.mapper.define;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineDepartmentMapper extends BaseMapper<DefineDepartment> {


    /**
     * [分页搜索查询] - 部门定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineDepartmentDto> selectQueryPage(Page<DefineDepartmentDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 查询部门(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineDepartment> getDepartmentFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);

    /**
     * 根据用户id查询 所属的部门详情
     * @param userAccountId
     * @param departmentState
     * @return
     */
    DefineDepartment selectOneOfUserBelongDepartment(@Param("userAccountId") String userAccountId, @Param("departmentState") Short departmentState);

    /**
     * 根据用户id查询 所属的部门详情-dto
     * @param userAccountId
     * @return
     */
    DefineDepartmentDto selectOneDtoOfUserBelongDepartment(@Param("userAccountId") String userAccountId);
}
