package com.egg.manager.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.user.UserRole;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserRoleService extends IService<UserRole> {

    public List<UserRole> selectByAccountId() ;
}
