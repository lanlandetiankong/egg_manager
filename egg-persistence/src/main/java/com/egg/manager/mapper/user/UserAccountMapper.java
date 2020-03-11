package com.egg.manager.mapper.user;

import com.egg.manager.entity.user.UserAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * [通用查询] 根据用户id查询用户entity
     * @return
     */
    UserAccount commonSelectUserAccountById() ;

    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds,@Param("loginUser")UserAccount loginUser) ;

    int batchLockUserByIds(@Param("lockIds")List<String> lockIds,@Param("lockState") int lockState,@Param("loginUser")UserAccount loginUser) ;

    /**
     * 批量伪删除 指定用户的所有角色关联
     * @param userAccountId
     * @return
     */
    int clearAllRoleByUserId(@Param("userAccountId") String userAccountId,@Param("loginUser") UserAccount loginUser) ;

    /**
     * 批量伪删除 指定用户的所有职务关联
     * @param userAccountId
     * @return
     */
    int clearAllJobByUserId(@Param("userAccountId")String userAccountId,@Param("loginUser") UserAccount loginUser) ;
}
