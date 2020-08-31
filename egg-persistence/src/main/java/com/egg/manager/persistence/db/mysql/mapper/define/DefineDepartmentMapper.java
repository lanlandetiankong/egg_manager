package com.egg.manager.persistence.db.mysql.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentDto;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定义的部门 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineDepartmentMapper extends BaseMapper<DefineDepartment> {


    /**
     * [分页搜索查询] - 部门定义
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineDepartmentDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 查询部门(过滤指定节点下的所有节点
     *
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<DefineDepartment> getDepartmentFilterChildrens(@Param("filterId") String filterId, @Param("onlyEnable") boolean onlyEnable);
}
