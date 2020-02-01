package com.egg.manager.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefinePermission;
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
public interface UserAccountService extends IService<UserAccount> {

    UserAccount dealGetAccountByDTO(LoginAccountDTO loginAccountDTO) ;

    /**
     * 取得 用户 的所有权限
     * @param userAccount
     * @return
     */
    List<DefinePermission> dealGetAllPermssionByAccount(UserAccount userAccount);

}
