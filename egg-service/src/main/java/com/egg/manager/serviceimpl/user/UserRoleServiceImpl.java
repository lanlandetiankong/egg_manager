package com.egg.manager.serviceimpl.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.user.UserRoleMapper;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.service.user.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    /**
     * TODO
     * @return
     */
    @Override
    public List<UserRole> selectByAccountId() {
        EntityWrapper<UserRole> ew = new EntityWrapper<>() ;
        ew.where("");

        return null;
    }



    /**
     * 取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccount(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        List<UserRole> userDefineRoles = dealGetAllUserRoleByAccountFromRedis(userAccount);
        if(userDefineRoles == null || userDefineRoles.isEmpty()) {
            userDefineRoles = dealGetAllUserRoleByAccountFromDb(userAccount);
        }
        return userDefineRoles;
    }
    /**
     * 从数据库是中取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromDb(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        EntityWrapper<UserRole> userRoleEm = new EntityWrapper<UserRole>() ;
        userRoleEm.where("state={0}", BaseStateEnum.ENABLED.getValue())
                .and("user_account_id={0}",userAccount.getFid());
        userRoleEm.orderBy("update_time",false);
        List<UserRole> userDefineRoles = selectList(userRoleEm);
        return userDefineRoles;
    }

    /**
     * 从Redis中取得当前用户关联的 UserRole
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromRedis(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        Object  userRoleListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserRoleKey(),userAccount.getFid()) ;
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<UserRole> userDefineRoles  = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<UserRole>>(){}) ;
        return userDefineRoles;
    }






    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true ;
        }
        return false;
    }
}
