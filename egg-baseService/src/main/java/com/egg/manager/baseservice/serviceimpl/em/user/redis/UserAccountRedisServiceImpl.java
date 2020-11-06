package com.egg.manager.baseservice.serviceimpl.em.user.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.em.define.basic.DefinePermissionService;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.services.em.user.redis.UserAccountRedisService;
import com.egg.manager.api.exchange.servicesimpl.redis.BaseRedisCommonReqServiceImpl;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.em.user.db.mysql.entity.DefineTenant;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = UserAccountRedisService.class)
public class UserAccountRedisServiceImpl extends BaseRedisCommonReqServiceImpl implements UserAccountRedisService {

    @Reference
    private RedisHelper redisHelper;

    @Autowired
    public UserAccountMapper userAccountMapper;

    @Autowired
    public UserAccountService userAccountService;


    @Reference
    public DefineRoleService defineRoleService;
    @Reference
    public DefinePermissionService definePermissionService;
    @Reference
    public DefineMenuService defineMenuService;


    /**
     * 根据 jwt的authorization值 取得 当前用户 Entity
     * @param authorization jwt值
     * @return
     */
    @Override
    public UserAccount dealGetCurrentLoginUserByAuthorization(UserAccount loginUser, String authorization) {
        if (StringUtils.isNotBlank(authorization)) {
            Object obj = redisHelper.hashGet(RedisShiroCacheEnum.authorization.getKey(), authorization);
            if (obj != null) {
                String objStr = "";
                if (obj instanceof String) {
                    objStr = (String) obj;
                } else {
                    objStr = JSONObject.toJSONString(obj);
                }
                UserAccountToken accountToken = JSONObject.parseObject(objStr, UserAccountToken.class);
                if (accountToken != null) {
                    Long accountId = accountToken.getUserAccountId();
                    return dealGetCurrentUserEntity(loginUser, authorization, accountId, false);
                }
            }
        }
        return null;
    }


    /**
     * 取得 当前用户 Entity
     * @param userAccountId
     * @return
     */
    @Override
    public UserAccount dealGetCurrentUserEntity(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
        return dealAutoGetRedisObjectCache(loginUser, RedisShiroCacheEnum.userAccount.getKey(), authorization, userAccountId, UserAccount.class,
                almostRefresh, RedisShiroCacheEnum.userAccount.getTtl());
    }

    @Override
    public DefineTenant dealGetCurrentLoginerBelongTenantByAuthorization(UserAccount loginUser, String authorization) {
        if (StringUtils.isNotBlank(authorization)) {
            Object obj = redisHelper.hashGet(RedisShiroCacheEnum.authorization.getKey(), authorization);
            if (obj != null) {
                String objStr = "";
                if (obj instanceof String) {
                    objStr = (String) obj;
                } else {
                    objStr = JSONObject.toJSONString(obj);
                }
                UserAccountToken accountToken = JSONObject.parseObject(objStr, UserAccountToken.class);
                if (accountToken != null) {
                    Long tenantId = accountToken.getUserBelongTenantId();
                    return dealGetCurrentUserBelongTenantEntity(loginUser, authorization, tenantId, false);
                }
            }
        }
        return null;
    }

    /**
     * 取得 当前用户所属租户 Entity
     * @param defineTenantId
     * @return
     */
    @Override
    public DefineTenant dealGetCurrentUserBelongTenantEntity(UserAccount loginUser, String authorization, Long defineTenantId, boolean almostRefresh) {
        return dealAutoGetRedisObjectCache(loginUser, RedisShiroCacheEnum.userTenant.getKey(), authorization, defineTenantId, DefineTenant.class,
                almostRefresh, RedisShiroCacheEnum.userTenant.getTtl());
    }

    /**
     * 取得 当前用户 的所有 角色-Set<String>
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetCurrentUserAllRoleSet(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
        List<String> roleCodeList = dealAutoGetRedisListCache(loginUser, RedisShiroCacheEnum.userRoles.getKey(), authorization, userAccountId, String.class, almostRefresh, RedisShiroCacheEnum.userRoles.getTtl());
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
    public Set<String> dealGetCurrentUserAllPermissionSet(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
        List<String> permissionCodeList = dealAutoGetRedisListCache(loginUser, RedisShiroCacheEnum.userPermissions.getKey(), authorization, userAccountId, String.class, almostRefresh, RedisShiroCacheEnum.userPermissions.getTtl());
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
    public Set<String> dealGetCurrentUserFrontRouterUrls(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
        List<String> menuCodeList = dealAutoGetRedisListCache(loginUser, RedisShiroCacheEnum.userFrontRouterUrl.getKey(), authorization, userAccountId, String.class, almostRefresh, RedisShiroCacheEnum.userFrontRouterUrl.getTtl());
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
    public List<CommonMenuTree> dealGetCurrentUserFrontMenuTrees(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
        List<CommonMenuTree> menuTreeList = dealAutoGetRedisListCache(loginUser, RedisShiroCacheEnum.userFrontMenus.getKey(), authorization, userAccountId, CommonMenuTree.class, almostRefresh, RedisShiroCacheEnum.userFrontMenus.getTtl());
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
    public Set<String> dealGetCurrentUserFrontButtons(UserAccount loginUser, String authorization, Long userAccountId, boolean almostRefresh) {
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
    public void dealRedisListCacheRefresh(UserAccount loginUser, String key, String hashKey, Long userAccountId, Long keyTtl) {
        if (RedisShiroCacheEnum.userRoles.getKey().equals(key)) {
            //用户拥有的[角色code-Set]
            Set<String> defineRoleCodeSet = defineRoleService.dealGetRoleCodeSetByAccountFromDb(userAccountId);
            if (keyTtl == null) {
                redisHelper.hashPut(key, hashKey, defineRoleCodeSet);
            } else {
                redisHelper.hashTtlPut(key, hashKey, defineRoleCodeSet, keyTtl);
            }
        } else if (RedisShiroCacheEnum.userPermissions.getKey().equals(key)) {
            //用户拥有的[权限code-Set]
            Set<String> definePermissionCodeSet = definePermissionService.dealGetPermissionCodeSetByAccountFromDb(loginUser, userAccountId);
            if (keyTtl == null) {
                redisHelper.hashPut(key, hashKey, definePermissionCodeSet);
            } else {
                redisHelper.hashTtlPut(key, hashKey, definePermissionCodeSet, keyTtl);
            }
        } else if (RedisShiroCacheEnum.userFrontButtons.getKey().equals(key)) {
            //用户拥有的[前端按钮code-Set]
            //TODO
        } else if (RedisShiroCacheEnum.userFrontRouterUrl.getKey().equals(key)) {
            //用户拥有的[RouterUrl-Set]
            Set<String> visitAbleUrlSet = defineMenuService.dealGetUserVisitAbleUrl(userAccountId);
            if (keyTtl == null) {
                redisHelper.hashPut(key, hashKey, visitAbleUrlSet);
            } else {
                redisHelper.hashTtlPut(key, hashKey, visitAbleUrlSet, keyTtl);
            }
        } else if (RedisShiroCacheEnum.userAccount.getKey().equals(key)) {
            //用户信息 缓存
            UserAccount userAccount = userAccountMapper.selectById(userAccountId);
            if (keyTtl == null) {
                redisHelper.hashPut(key, hashKey, userAccount);
            } else {
                redisHelper.hashTtlPut(key, hashKey, userAccount, keyTtl);
            }
        } else if (RedisShiroCacheEnum.userFrontMenus.getKey().equals(key)) {
            //用户可访问 菜单树
            List<CommonMenuTree> treeList = defineMenuService.dealGetUserGrantedMenuTrees(userAccountId);
            if (keyTtl == null) {
                redisHelper.hashPut(key, hashKey, treeList);
            } else {
                redisHelper.hashTtlPut(key, hashKey, treeList, keyTtl);
            }
        }
    }


}
