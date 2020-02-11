package com.egg.manager.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.user.UserJob;
import com.egg.manager.entity.user.UserRole;
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
    //批量 伪删除
    int batchFakeDelByIds(List<String> delIds) ;

    /**
     * 取得用户拥有的所有职务id集合
     * @param userAccountId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllJobIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable") boolean filterEnable) ;


    /**
     * 批量新增 用户-职务 关联
     * @param jobList
     * @return
     */
    int customBatchInsert(List<UserJob> jobList);

    /**
     * 根据用户id 修改指定职务关联 的可用状态
     * @param userAccountId
     * @param jobIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId, @Param("jobIdList") List<String> jobIdList, @Param("stateVal") Integer stateVal);
}
