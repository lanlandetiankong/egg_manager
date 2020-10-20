package com.egg.manager.persistence.db.mysql.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户&部门 Mapper 接口
 * </p>
 *
 * @author zhouchengjie
 */
public interface UserDepartmentMapper extends BaseMapper<UserDepartment> {
    /**
     * [分页搜索查询] - 用户&部门
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserDepartmentDto> selectQueryPage(Page<UserDepartmentDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有部门id集合
     *
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 批量新增 用户-部门 关联
     *
     * @param list
     * @return
     */
    int customBatchInsert(List<UserDepartment> list);

    /**
     * 根据用户id 修改指定部门关联 的可用状态
     *
     * @param userAccountId
     * @param departmentIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("departmentIdList") List<String> departmentIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
