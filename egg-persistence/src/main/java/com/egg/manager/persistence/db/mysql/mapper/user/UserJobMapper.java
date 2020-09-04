package com.egg.manager.persistence.db.mysql.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserJobDto;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户-职务 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserJobMapper extends BaseMapper<UserJob> {

    /**
     * [分页搜索查询] - 用户职务
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<UserJobDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);

    /**
     * 取得用户拥有的所有职务id集合
     *
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllJobIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 批量新增 用户-职务 关联
     *
     * @param jobList
     * @return
     */
    int customBatchInsert(List<UserJob> jobList);

    /**
     * 根据用户id 修改指定职务关联 的可用状态
     *
     * @param userAccountId
     * @param jobIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("jobIdList") List<String> jobIdList, @Param("stateVal") Short stateVal,
                                        @Param("loginUser") UserAccount loginUser);
}
