package com.egg.manager.persistence.mapper.user;

import com.egg.manager.persistence.entity.define.DefineGroup;
import com.egg.manager.persistence.entity.user.UserGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {


    DefineGroup doQueryAbleGroupByUserId(@Param("userId")String userId);
}
