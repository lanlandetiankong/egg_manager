package com.egg.manager.mapper.user;

import com.egg.manager.entity.user.UserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
}
