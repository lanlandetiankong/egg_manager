package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.enhance.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartment;
import com.egg.manager.persistence.em.user.pojo.dto.UserDepartmentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserDepartmentMapper extends MyEggMapper<UserDepartment> {
    /**
     * [分页搜索查询] - 用户&部门
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserDepartmentDto> selectQueryPage(Page<UserDepartmentDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 取得用户拥有的所有部门id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllIdByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("filterEnable") boolean filterEnable);

    /**
     * 批量新增 用户-部门 关联
     * @param list
     * @return
     */
    int customBatchInsert(List<UserDepartment> list);

    /**
     * 根据用户id 修改指定部门关联 的可用状态
     * @param userAccountId
     * @param departmentIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("departmentIdList") List<String> departmentIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);
}
