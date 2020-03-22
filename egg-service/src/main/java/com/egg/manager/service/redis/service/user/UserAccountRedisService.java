package com.egg.manager.service.redis.service.user;

import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.service.redis.service.common.MyRedisCommonReqService;

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
    UserAccount dealGetCurrentLoginUserByAuthorization(String authorization) ;

    /**
     * 取得 当前用户 Entity
     * @param userAccountId
     * @return
     */
    UserAccount dealGetCurrentUserEntity(String authorization, String userAccountId, boolean almostRefresh);

    /**
     *  取得 当前用户 的所有 角色-Set<String>
     * @return
     */
    Set<String> dealGetCurrentUserAllRoleSet(String authorization,String userAccountId,boolean almostRefresh) ;

    /**
     *  取得 当前用户 的所有 权限-Set<String>
     * @return
     */
    Set<String>  dealGetCurrentUserAllPermissionSet(String authorization,String userAccountId,boolean almostRefresh);




    /**
     *  取得 当前用户 的所有 菜单-List<String>
     * @return
     */
    Set<String> dealGetCurrentUserFrontMenus(String authorization,String userAccountId,boolean almostRefresh);


    /**
     *  取得 当前用户 的所有 按钮-Set<String>
     * @return
     */
    Set<String> dealGetCurrentUserFrontButtons(String authorization,String userAccountId,boolean almostRefresh);


}
