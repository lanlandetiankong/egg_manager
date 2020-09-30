package com.egg.manager.api.services.redis.service.user;

import com.egg.manager.api.services.redis.service.common.MyRedisCommonReqService;
import com.egg.manager.persistence.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:35
 * \* Description:
 * \
 */
public interface UserAccountRedisService extends MyRedisCommonReqService {
    /**
     * 根据 jwt的authorization值 取得 当前用户 Entity
     * @param loginUser 当前登录用户
     * @param authorization jwt值
     * @return
     */
    UserAccount dealGetCurrentLoginUserByAuthorization(UserAccount loginUser,String authorization) ;

    /**
     * 取得 当前用户 Entity
     * @param loginUser 当前登录用户
     * @param authorization 授权token值
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    UserAccount dealGetCurrentUserEntity(UserAccount loginUser,String authorization, String userAccountId, boolean almostRefresh);

    /**
     * 根据 jwt的authorization值 取得 当前租户 Entity
     * @param loginUser 当前登录用户
     * @param authorization jwt值
     * @return
     */
    DefineTenant dealGetCurrentLoginerBelongTenantByAuthorization(UserAccount loginUser,String authorization) ;
    /**
     * 根据 jwt的authorization值 取得 当前用户所属租户 Entity
     * @param loginUser 当前登录用户
     * @param authorization jwt值
     * @param defineTenantId
     * @param almostRefresh
     * @return
     */
    DefineTenant dealGetCurrentUserBelongTenantEntity(UserAccount loginUser,String authorization,String defineTenantId,boolean almostRefresh);

    /**
     * 取得 当前用户 的所有 角色-Set<String>
     * @param loginUser 当前登录用户
     * @param authorization
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    Set<String> dealGetCurrentUserAllRoleSet(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh) ;

    /**
     * 取得 当前用户 的所有 权限-Set<String>
     * @param loginUser 当前登录用户
     * @param authorization
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    Set<String>  dealGetCurrentUserAllPermissionSet(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);


    /**
     * 取得 当前用户 的所有 菜单-List<String>
     * @param loginUser 当前登录用户
     * @param authorization
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    Set<String> dealGetCurrentUserFrontRouterUrls(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);

    /**
     * 取得 当前用户 index界面展示的菜单列表-List<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param loginUser 当前登录用户
     * @param authorization 认证值
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    List<CommonMenuTree> dealGetCurrentUserFrontMenuTrees(UserAccount loginUser,String authorization, String userAccountId, boolean almostRefresh);

    /**
     * 取得 当前用户 的所有 按钮-Set<String>
     * @param loginUser 当前登录用户
     * @param authorization
     * @param userAccountId
     * @param almostRefresh
     * @return
     */
    Set<String> dealGetCurrentUserFrontButtons(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);


}
