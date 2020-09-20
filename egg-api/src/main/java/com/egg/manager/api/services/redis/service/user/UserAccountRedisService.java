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
     * @param authorization jwt值
     * @return
     */
    UserAccount dealGetCurrentLoginUserByAuthorization(UserAccount loginUser,String authorization) ;

    /**
     * 取得 当前用户 Entity
     * @param userAccountId
     * @return
     */
    UserAccount dealGetCurrentUserEntity(UserAccount loginUser,String authorization, String userAccountId, boolean almostRefresh);

    /**
     * 根据 jwt的authorization值 取得 当前租户 Entity
     * @param authorization jwt值
     * @return
     */
    DefineTenant dealGetCurrentLoginerBelongTenantByAuthorization(UserAccount loginUser,String authorization) ;
    /**
     * 根据 jwt的authorization值 取得 当前用户所属租户 Entity
     * @param authorization jwt值
     * @return
     */
    DefineTenant dealGetCurrentUserBelongTenantEntity(UserAccount loginUser,String authorization,String defineTenantId,boolean almostRefresh);

    /**
     *  取得 当前用户 的所有 角色-Set<String>
     * @return
     */
    Set<String> dealGetCurrentUserAllRoleSet(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh) ;

    /**
     *  取得 当前用户 的所有 权限-Set<String>
     * @return
     */
    Set<String>  dealGetCurrentUserAllPermissionSet(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);




    /**
     *  取得 当前用户 的所有 菜单-List<String>
     * @return
     */
    Set<String> dealGetCurrentUserFrontRouterUrls(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);

    /**
     * 取得 当前用户 index界面展示的菜单列表-List<String>
     * 如果取得为空的话会 自动刷新缓存
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> dealGetCurrentUserFrontMenuTrees(UserAccount loginUser,String authorization, String userAccountId, boolean almostRefresh);
    /**
     *  取得 当前用户 的所有 按钮-Set<String>
     * @return
     */
    Set<String> dealGetCurrentUserFrontButtons(UserAccount loginUser,String authorization,String userAccountId,boolean almostRefresh);


}
