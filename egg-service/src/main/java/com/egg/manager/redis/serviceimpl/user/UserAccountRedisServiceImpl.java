package com.egg.manager.redis.serviceimpl.user;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefineRoleMapper;
import com.egg.manager.redis.service.RedisHelper;
import com.egg.manager.redis.service.user.UserAccountRedisService;
import com.egg.manager.redis.serviceimpl.common.MyRedisCommonReqServiceImpl;
import com.egg.manager.service.define.DefinePermissionService;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.webvo.session.UserAccountToken;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Service("userAccountRedisService")
public class UserAccountRedisServiceImpl extends MyRedisCommonReqServiceImpl implements UserAccountRedisService {

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    @Autowired
    public UserAccountService userAccountService ;


    @Autowired
    public DefineRoleService defineRoleService ;
    @Autowired
    public DefinePermissionService definePermissionService ;

    /**
     * 根据 jwt的authorization值 取得 当前用户 Entity
     * @param authorization jwt值
     * @return
     */
    @Override
    public UserAccount dealGetCurrentLoginUserByAuthorization(String authorization) {
        if(StringUtils.isNotBlank(authorization)){
            Object obj = redisHelper.hashGet(redisPropsOfShiroCache.getAuthorizationKey(),authorization);
            if(obj != null){
                String objStr = "" ;
                if(obj instanceof String){
                    objStr = (String) obj ;
                }   else {
                    objStr = JSONObject.toJSONString(obj);
                }
                UserAccountToken accountToken = JSONObject.parseObject(objStr,UserAccountToken.class) ;
                if(accountToken != null){
                    String accountId = accountToken.getUserAccountId();
                    return dealGetCurrentUserEntity(authorization,accountId,false);
                }
            }
        }
        return null ;
    }


    /**
     * 取得 当前用户 Entity
     *
     * @param userAccountId
     * @return
     */
    @Override
    public UserAccount dealGetCurrentUserEntity(String authorization,String userAccountId,boolean almostRefresh) {
        return dealAutoGetRedisObjectCache(redisPropsOfShiroCache.getUserAccountKey(),authorization,userAccountId,UserAccount.class,almostRefresh);
    }

    /**
     * 取得 当前用户 的所有 角色-Set<String>
     *
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserAllRoleSet(String authorization,String userAccountId,boolean almostRefresh) {
        Set<String> roleSet = Sets.newHashSet();
        List<String> roleCodeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserRolesKey(),authorization,userAccountId,String.class,almostRefresh);
        roleSet.addAll(roleCodeList) ;
        return roleSet;
    }

    /**
     * 取得 当前用户 的所有 权限-Set<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserAllPermissionSet(String authorization,String userAccountId,boolean almostRefresh) {
        Set<String> permissionSet = Sets.newHashSet();
        List<String> permissionCodeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserPermissionsKey(),authorization,userAccountId,String.class,almostRefresh);
        permissionSet.addAll(permissionCodeList) ;
        return permissionSet;
    }

    /**
     * 取得 当前用户 的所有 菜单-List<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserFrontMenus(String authorization,String userAccountId,boolean almostRefresh) {
        Set<String> buttonsSet = Sets.newHashSet();
        return buttonsSet;
    }

    /**
     * 取得 当前用户 的所有 按钮-Set<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserFrontButtons(String authorization,String userAccountId,boolean almostRefresh) {
        Set<String> menusSet = Sets.newHashSet();
        return menusSet;
    }

    /**
     * 刷新缓存 处理
     * @param key
     * @param hashKey
     * @param userAccountId
     */
    @Override
    public void dealRedisListCacheRefresh(String key,String hashKey,String userAccountId){
        if(redisPropsOfShiroCache.getUserRolesKey().equals(key)){    //用户拥有的[角色code-Set]
            Set<String> defineRoleCodeSet = defineRoleService.dealGetRoleCodeSetByAccountFromDb(userAccountId);
            redisHelper.hashPut(key,hashKey,defineRoleCodeSet);
        }   else if(redisPropsOfShiroCache.getUserPermissionsKey().equals(key)){ //用户拥有的[权限code-Set]
            Set<String> definePermissionCodeSet = definePermissionService.dealGetPermissionCodeSetByAccountFromDb(userAccountId);
            redisHelper.hashPut(key,hashKey,definePermissionCodeSet);
        }  else if(redisPropsOfShiroCache.getUserFrontButtonsKey().equals(key)){    //用户拥有的[前端按钮code-Set]
            //TODO
        }  else if(redisPropsOfShiroCache.getUserFrontMenusKey().equals(key)){  //用户拥有的[菜单按钮code-Set]
            //TODO
        }   else if(redisPropsOfShiroCache.getUserAccountKey().equals(key)){    //用户信息 缓存
            UserAccount userAccount = userAccountService.selectById(userAccountId);
            redisHelper.hashPut(key,hashKey,userAccount);
        }
    }


}
