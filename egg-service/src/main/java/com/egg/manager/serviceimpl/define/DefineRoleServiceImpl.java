package com.egg.manager.serviceimpl.define;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.define.DefineRoleMapper;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.service.user.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefineRoleServiceImpl extends ServiceImpl<DefineRoleMapper,DefineRole> implements DefineRoleService {

    @Autowired
    private UserRoleService userRoleService ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Autowired
    private RedisHelper redisHelper ;

    @Override
    public List<DefineRole> dealGetRolesByAccount(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return null ;
        }

        return null;
    }
    @Override
    public List<DefineRole> dealGetAllDefineRoles() {
        List<DefineRole> allDefineRoles = dealGetAllDefineRolesFromRedis(false);
        if(allDefineRoles == null || allDefineRoles.isEmpty()) {
            allDefineRoles = dealGetAllDefineRolesFromDb() ;
        }
        return allDefineRoles ;
    }

    @Override
    public List<DefineRole> dealGetAllDefineRolesFromDb() {
        EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>() ;
        defineRoleEntityWrapper.where("state={0}",BaseStateEnum.ENABLED.getValue()) ;
        return selectList(defineRoleEntityWrapper);
    }

    /**
     * 从 redis 中取得所有 角色
     * @param refreshRedis  是否先刷新redis缓存
     * @return
     */
    @Override
    public List<DefineRole> dealGetAllDefineRolesFromRedis(boolean refreshRedis) {
        if(refreshRedis == true) {
            redisHelper.valuePut(redisPropsOfShiroCache.getDefineRoleAllKey(),dealGetAllDefineRolesFromDb()) ;
        }
        Object allDefineRoleObj = redisHelper.getValue(redisPropsOfShiroCache.getDefineRoleAllKey()) ;
        String allDefineRoleJson = JSONObject.toJSONString(allDefineRoleObj);
        List<DefineRole> allDefineRoles  = JSON.parseObject(allDefineRoleJson, new TypeReference<ArrayList<DefineRole>>(){}) ;
        return allDefineRoles ;
    }

    /**
     * 根据 用户账号 取得所有角色
     * @param userAccount
     * @return
     */
    @Override
    public List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) {
        List<UserRole> userRoles = userRoleService.dealGetAllUserRoleByAccount(userAccount);
        List<DefineRole> defineRoles = null ;
        if(userRoles == null || userRoles.isEmpty()) {
            return defineRoles;
        }   else {
            defineRoles = new ArrayList<DefineRole>() ;
            Set<String> roleIds = new HashSet<String>();
            for(UserRole userRole : userRoles){
                if(StringUtils.isNotBlank(userRole.getDefineRoleId())){
                    roleIds.add(userRole.getDefineRoleId());
                }
            }
            EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>() ;
            defineRoleEntityWrapper.where("state={0}",BaseStateEnum.ENABLED.getValue())
                    .in(true,"define_role_id",roleIds);
            defineRoles = selectList(defineRoleEntityWrapper);
        }
        return defineRoles ;
    }
}
