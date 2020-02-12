package com.egg.manager.mapper.define;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定义的职务 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineJobMapper extends BaseMapper<DefineJob> {


    /**
     * 批量 伪删除
     */
    int batchFakeDelByIds(@Param("delIds")List<String> delIds,@Param("loginUser")UserAccount loginUser) ;



    /**
     * 查询指定用户的 用户-职务 关联表
     * @param userAccountId
     * @param stateVal 指定state的值
     * @return
     */
    List<DefineJob> findAllJobByUserAcccountId(@Param("userAccountId") String userAccountId, @Param("stateVal")Integer stateVal) ;
}
