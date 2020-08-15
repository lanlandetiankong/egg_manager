package com.egg.manager.api.service.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.entity.user.UserRole;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.vo.user.UserRoleVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> dealGetAllUserRoleByAccount(UserAccount account);

    List<UserRole> dealGetAllUserRoleByAccountFromDb(UserAccount userAccount);

    List<UserRole> dealGetAllUserRoleByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户角色列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetUserRolePages(MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户角色 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetUserRoleDtoPages(MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                        List<AntdvSortBean> sortBeans);


    /**
     * 用户角色-新增
     * @param userRoleVo
     * @throws Exception
     */
    Integer dealAddUserRole(UserRoleVo userRoleVo,UserAccount loginUser) throws Exception;

    /**
     * 用户角色-更新
     * @param userRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateUserRole(UserRoleVo userRoleVo,UserAccount loginUser,boolean updateAll) throws Exception;

    /**
     * 用户角色-删除
     * @param delIds 要删除的用户角色id 集合
     * @throws Exception
     */
    Integer dealDelUserRoleByArr(String[] delIds,UserAccount loginUser) throws Exception ;

    /**
     * 用户角色-删除
     * @param delId 要删除的用户角色id
     * @throws Exception
     */
    Integer dealDelUserRole(String delId,UserAccount loginUser) throws Exception ;
}
