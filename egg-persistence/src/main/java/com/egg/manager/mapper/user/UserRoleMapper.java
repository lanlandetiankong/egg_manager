package com.egg.manager.mapper.user;

import com.egg.manager.entity.role.RolePermission;
import com.egg.manager.entity.user.UserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    //批量 伪删除
    int batchFakeDelByIds(List<String> delIds) ;
    /**
     * 取得用户拥有的所有角色id集合
     * @param userAccountId
     * @param filterEnable 是否只查询状态为可用的
     * @return
     */
    List<String> findAllRoleIdByUserAccountId(@Param("userAccountId") String userAccountId, @Param("filterEnable")boolean filterEnable) ;


    /**
     * 批量新增 用户-角色 关联
     * @param roleList
     * @return
     */
    int customBatchInsert(List<UserRole> roleList);

    /**
     * 根据用户id 修改指定角色关联 的可用状态
     * @param userAccountId
     * @param roleIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param("userAccountId") String userAccountId,@Param("roleIdList")List<String> roleIdList,@Param("stateVal")Integer stateVal);
}
