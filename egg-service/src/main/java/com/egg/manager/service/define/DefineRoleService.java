package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineRoleService extends IService<DefineRole> {

    List<DefineRole> dealGetRolesByAccount(UserAccount userAccount) ;

    List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) ;

    List<DefineRole> dealGetAllDefineRoles();

    List<DefineRole> dealGetAllDefineRolesFromDb();

    List<DefineRole> dealGetAllDefineRolesFromRedis(boolean refreshRedis);

}
