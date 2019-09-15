package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.user.UserRoleMapper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.service.user.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {

    @Override
    public List<UserRole> selectByAccountId() {
        EntityWrapper<UserRole> ew = new EntityWrapper<>() ;
        ew.where("");

        return null;
    }
}
