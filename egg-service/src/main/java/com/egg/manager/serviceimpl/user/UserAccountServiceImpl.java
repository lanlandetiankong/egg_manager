package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.user.UserAccountService;
import org.springframework.stereotype.Service;

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

    
}
