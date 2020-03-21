package com.egg.manager.redis.service.user;

import com.egg.manager.redis.service.common.MyRedisCommonReqService;

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
