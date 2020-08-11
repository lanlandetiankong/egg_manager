package com.egg.manager.service.redis.serviceimpl.user;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.tree.CommonMenuTree;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.redis.service.user.UserAccountRedisService;
import com.egg.manager.service.redis.serviceimpl.common.MyRedisCommonReqServiceImpl;
import com.egg.manager.service.service.define.DefinePermissionService;
import com.egg.manager.service.service.define.DefineRoleService;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.persistence.webvo.session.UserAccountToken;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    public DefineMenuService defineMenuService ;

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
        return dealAutoGetRedisObjectCache(redisPropsOfShiroCache.getUserAccountKey(),authorization,userAccountId,UserAccount.class,
                                almostRefresh,redisPropsOfShiroCache.getUserAccountTtl());
    }

    /**
     * 取得 当前用户 的所有 角色-Set<String>
     *
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserAllRoleSet(String authorization,String userAccountId,boolean almostRefresh) {
        List<String> roleCodeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserRolesKey(),authorization,userAccountId,String.class,almostRefresh,redisPropsOfShiroCache.getUserRolesTtl());
        roleCodeList = roleCodeList != null ? roleCodeList : new ArrayList<String>();
        return Sets.newHashSet(roleCodeList);
    }

    /**
     * 取得 当前用户 的所有 权限-Set<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserAllPermissionSet(String authorization,String userAccountId,boolean almostRefresh) {
        List<String> permissionCodeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserPermissionsKey(),authorization,userAccountId,String.class,almostRefresh,redisPropsOfShiroCache.getUserPermissionsTtl());
        permissionCodeList = permissionCodeList != null ? permissionCodeList : new ArrayList<String>();
        return Sets.newHashSet(permissionCodeList);
    }

    /**
     * 取得 当前用户 的所有 routerUrl-List<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserFrontRouterUrls(String authorization,String userAccountId,boolean almostRefresh) {
        List<String> menuCodeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserFrontRouterUrlKey(),authorization,userAccountId,String.class,almostRefresh,redisPropsOfShiroCache.getUserFrontRouterUrlTtl());
        menuCodeList = menuCodeList != null ? menuCodeList : new ArrayList<String>();
        return Sets.newHashSet(menuCodeList);
    }


    /**
     * 取得 当前用户 index界面展示的菜单列表-List<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    @Override
    public List<CommonMenuTree> dealGetCurrentUserFrontMenuTrees(String authorization,String userAccountId,boolean almostRefresh) {
        List<CommonMenuTree> menuTreeList = dealAutoGetRedisListCache(redisPropsOfShiroCache.getUserFrontMenusKey(),authorization,userAccountId,CommonMenuTree.class,almostRefresh,redisPropsOfShiroCache.getUserFrontMenusTtl());
        menuTreeList = menuTreeList != null ? menuTreeList : new ArrayList<CommonMenuTree>();
        return Lists.newArrayList(menuTreeList);
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
    public void dealRedisListCacheRefresh(String key,String hashKey,String userAccountId,Long keyTtl){
        if(redisPropsOfShiroCache.getUserRolesKey().equals(key)){    //用户拥有的[角色code-Set]
            Set<String> defineRoleCodeSet = defineRoleService.dealGetRoleCodeSetByAccountFromDb(userAccountId);
            if(keyTtl == null){
                redisHelper.hashPut(key,hashKey,defineRoleCodeSet);
            }   else {
                redisHelper.hashTtlPut(key,hashKey,defineRoleCodeSet,keyTtl);
            }
        }   else if(redisPropsOfShiroCache.getUserPermissionsKey().equals(key)){ //用户拥有的[权限code-Set]
            Set<String> definePermissionCodeSet = definePermissionService.dealGetPermissionCodeSetByAccountFromDb(userAccountId);
            if(keyTtl == null){
                redisHelper.hashPut(key,hashKey,definePermissionCodeSet);
            }   else {
                redisHelper.hashTtlPut(key,hashKey,definePermissionCodeSet,keyTtl);
            }
        }  else if(redisPropsOfShiroCache.getUserFrontButtonsKey().equals(key)){    //用户拥有的[前端按钮code-Set]
            //TODO
        }  else if(redisPropsOfShiroCache.getUserFrontRouterUrlKey().equals(key)){  //用户拥有的[RouterUrl-Set]
            Set<String> visitAbleUrlSet =  defineMenuService.dealGetUserVisitAbleUrl(userAccountId);
            if(keyTtl == null){
                redisHelper.hashPut(key,hashKey,visitAbleUrlSet);
            }   else {
                redisHelper.hashTtlPut(key,hashKey,visitAbleUrlSet,keyTtl);
            }
        }   else if(redisPropsOfShiroCache.getUserAccountKey().equals(key)){    //用户信息 缓存
            UserAccount userAccount = userAccountService.selectById(userAccountId);
            if(keyTtl == null){
                redisHelper.hashPut(key,hashKey,userAccount);
            }   else {
                redisHelper.hashTtlPut(key,hashKey,userAccount,keyTtl);
            }
        }   else if(redisPropsOfShiroCache.getUserFrontMenusKey().equals(key)){     //用户可访问 菜单树
            List<CommonMenuTree> treeList = defineMenuService.dealGetUserGrantedMenuTrees(userAccountId);
            if(keyTtl == null){
                redisHelper.hashPut(key,hashKey,treeList);
            }   else {
                redisHelper.hashTtlPut(key,hashKey,treeList,keyTtl);
            }
        }
    }


}
