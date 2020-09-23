package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;

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
public interface DefineRoleService extends IService<DefineRole>,MyBaseMysqlService<DefineRoleMapper,DefineRole,DefineRoleVo> {
    /**
     * 取得用户 所拥有的 角色定义-List集合
     * @param userAccountId
     * @return
     */
    List<DefineRole> dealGetRolesByAccountFromDb(String userAccountId,Short stateVal) ;

    /**
     * 取得用户 所拥有的 角色code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> dealGetRoleCodeSetByAccountFromDb(String userAccountId);

    /**
     * 取得角色 所拥有的 菜单定义-List集合
     * @param roleId
     * @param stateVal 状态值
     * @return
     */
    List<DefineMenu> dealGetMenusByRoleIdFromDb(String roleId, Short stateVal);

    /**
     * 取得角色 所拥有的 菜单定义id-Set集合
     * @param roleId
     * @param stateVal 状态值
     * @return
     */
    Set<String> dealGetMenuIdSetByRoleIdFromDb(String roleId,Short stateVal) ;

    /**
     * 查询 所有[可用状态]的 [角色定义]
     * @param wrapper
     * @return
     */
    List<DefineRole> getAllEnableDefineRoles(QueryWrapper<DefineRole> wrapper);

    List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) ;







    /**
     * 分页查询 角色定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefineRoleVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                        List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 角色定义 列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefineRoleVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                           List<AntdvSortBean> sortBeans);

    /**
     * 角色定义-新增
     * @param defineRoleVo
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser,DefineRoleVo defineRoleVo) throws Exception ;

    /**
     * 角色定义-更新
     * @param defineRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser,DefineRoleVo defineRoleVo,boolean updateAll) throws Exception ;

    /**
     * 角色定义-批量删除
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception;

    /**
     * 角色定义-删除
     * @param delId 要删除的角色id
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception;

    /**
     * 角色授权
     * @param roleId 要授权的角色id
     * @param checkIds 权限id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealGrantPermissionToRole(UserAccount loginUser,String roleId,String[] checkIds) throws Exception;

}
