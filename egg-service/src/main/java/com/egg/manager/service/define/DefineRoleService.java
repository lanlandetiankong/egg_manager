package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefineRoleVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineRoleService extends IService<DefineRole> {

    List<DefineRole> dealGetRolesByAccount(UserAccount userAccount) ;

    List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) ;

    List<DefineRole> dealGetAllDefineRoles();

    List<DefineRole> dealGetAllDefineRolesFromDb();

    List<DefineRole> dealGetAllDefineRolesFromRedis(boolean refreshRedis);





    /**
     * 分页查询 角色
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineRolePages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean);

    /**
     * 角色定义-新增
     * @param defineRoleVo
     * @throws Exception
     */
    Integer dealAddDefineRole(DefineRoleVo defineRoleVo) throws Exception ;

    /**
     * 角色定义-更新
     * @param defineRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineRole(DefineRoleVo defineRoleVo,boolean updateAll) throws Exception ;

    /**
     * 角色定义-批量删除
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    Integer dealDelDefineRoleByArr(String[] delIds) throws Exception;

    /**
     * 角色定义-删除
     * @param delId 要删除的角色id
     * @throws Exception
     */
    Integer dealDelDefineRole(String delId) throws Exception;

    /**
     * 角色授权
     * @param roleId 要授权的角色id
     * @param checkIds 权限id集合
     * @throws Exception
     */
    Integer dealGrantPermissionToRole(String roleId,String[] checkIds,String userId) throws Exception;

}
