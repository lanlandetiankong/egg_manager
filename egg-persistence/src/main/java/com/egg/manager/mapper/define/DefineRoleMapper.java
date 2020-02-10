package com.egg.manager.mapper.define;

import com.egg.manager.entity.define.DefineRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色定义表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineRoleMapper extends BaseMapper<DefineRole> {
    //批量 伪删除
    int batchFakeDelByIds(List<String> delIds) ;

    /**
     * 查询指定用户的 用户-角色 关联表
     * @param userAccountId
     * @param stateVal 指定state的值
     * @return
     */
    List<DefineRole> findAllRoleByUserAcccountId(@Param("userAccountId") String userAccountId,@Param("stateVal")Integer stateVal) ;
}
