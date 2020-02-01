package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.user.UserAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper,UserAccount> implements UserAccountService{

    /**
     * 根据 LoginAccountDTO 取得对应的 UserAccount
     * @param loginAccountDTO
     * @return
     */
    @Override
    public UserAccount dealGetAccountByDTO(LoginAccountDTO loginAccountDTO) {
        EntityWrapper<UserAccount> wrapper = new EntityWrapper<UserAccount>() ;
        wrapper.setEntity(new UserAccount());
        wrapper.where("account={0}",loginAccountDTO.getAccount())
                .and("state>{0}", UserAccountStateEnum.DELETE) ;
        return selectOne(wrapper);
    }

    /**
     * TODO 取得 用户 的所有权限
     * @param userAccount 用户账号
     * @return
     */
    @Override
    public List<DefinePermission> dealGetAllPermssionByAccount(UserAccount userAccount) {

        return  null ;
    }
}
