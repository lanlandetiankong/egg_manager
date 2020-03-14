package com.egg.manager.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.user.UserAccountVo;
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
public interface UserAccountService extends IService<UserAccount> {

    UserAccount dealGetAccountByDTO(LoginAccountDTO loginAccountDTO) ;

    /**
     * 取得 用户 的所有权限
     * @param userAccount
     * @return
     */
    List<DefinePermission> dealGetAllPermssionByAccount(UserAccount userAccount);



    /**
     * 分页查询 用户列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetUserAccountPages(MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                 List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetUserAccountDtoPages(MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                 List<AntdvSortBean> sortBeans);

    /**
     * 用户账号-新增
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealAddUserAccount(UserAccountVo userAccountVo,UserAccount loginUser) throws Exception ;

    /**
     * 用户账号-更新
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @param updateAll 是否更新所有字段
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealUpdateUserAccount(UserAccountVo userAccountVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 用户账号-删除
     * @param delIds 要删除的用户id 集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealDelUserAccountByArr(String[] delIds,UserAccount loginUser) throws Exception ;

    /**
     * 用户账号-删除
     * @param delId 要删除的用户id
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealDelUserAccount(String delId,UserAccount loginUser) throws Exception ;



    /**
     * 用户账号-锁定
     * @param lockIds 要锁定的用户账号id 集合
     * @param isLock 是否锁定
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealLockUserAccountByArr(String[] lockIds,UserAccount loginUser,boolean isLock) throws Exception ;
    /**
     * 用户账号-锁定
     * @param lockId 要锁定的用户账号id
     * @param isLock 是否锁定
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealLockUserAccount(String lockId,UserAccount loginUser,boolean isLock) throws Exception ;


    /**
     * 用户分配角色
     * @param userAccountId 用户id
     * @param checkIds 角色id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealGrantRoleToUser(String userAccountId,String[] checkIds,UserAccount loginUser) throws Exception;

    /**
     * 用户分配职务
     * @param userAccountId 用户id
     * @param checkIds 职务id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    Integer dealGrantJobToUser(String userAccountId,String[] checkIds,UserAccount loginUser) throws Exception;
}
