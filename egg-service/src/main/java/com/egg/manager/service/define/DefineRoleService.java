package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefineRoleVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

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
public interface DefineRoleService extends IService<DefineRole> {
    /**
     * 取得用户 所拥有的 角色定义-List集合
     * @param userAccountId
     * @return
     */
    List<DefineRole> dealGetRolesByAccountFromDb(String userAccountId) ;

    /**
     * 取得用户 所拥有的 角色code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> dealGetRoleCodeSetByAccountFromDb(String userAccountId);

    List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) ;

    List<DefineRole> dealGetAllDefineRoles();

    List<DefineRole> dealGetAllDefineRolesFromDb();

    List<DefineRole> dealGetAllDefineRolesFromRedis(boolean refreshRedis);





    /**
     * 分页查询 角色定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineRolePages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 角色定义 列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineRoleDtoPages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                List<AntdvSortBean> sortBeans);

    /**
     * 角色定义-新增
     * @param defineRoleVo
     * @throws Exception
     */
    Integer dealAddDefineRole(DefineRoleVo defineRoleVo,UserAccount loginUser) throws Exception ;

    /**
     * 角色定义-更新
     * @param defineRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineRole(DefineRoleVo defineRoleVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 角色定义-批量删除
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    Integer dealDelDefineRoleByArr(String[] delIds,UserAccount loginUser) throws Exception;

    /**
     * 角色定义-删除
     * @param delId 要删除的角色id
     * @throws Exception
     */
    Integer dealDelDefineRole(String delId,UserAccount loginUser) throws Exception;

    /**
     * 角色授权
     * @param roleId 要授权的角色id
     * @param checkIds 权限id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealGrantPermissionToRole(String roleId,String[] checkIds,UserAccount loginUser) throws Exception;

}
