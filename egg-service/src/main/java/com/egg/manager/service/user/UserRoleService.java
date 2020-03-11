package com.egg.manager.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.vo.user.UserRoleVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

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

    public List<UserRole> selectByAccountId() ;

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
