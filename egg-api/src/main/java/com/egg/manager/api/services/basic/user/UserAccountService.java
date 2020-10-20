package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;

import java.util.List;
import java.util.Set;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserAccountService extends IService<UserAccount>,MyBaseMysqlService<UserAccount,UserAccountMapper,UserAccountVo> {

    String FOREIGN_NAME_OF_USER_TENANT = "userTenant" ;
    String FOREIGN_NAME_OF_USER_DEPARTMENT = "userDepartment" ;


    /**
     * 根据LoginAccountDTO查询对应的用户账号entity
     * @param loginAccountDTO
     * @return
     */
    UserAccount dealGetEntityByDTO(LoginAccountDTO loginAccountDTO) ;


    /**
     * 分页查询 用户列表
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserAccountVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccount> paginationBean,
                                                          List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserAccountVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccountDto> paginationBean,
                                                             List<AntdvSortBean> sortBeans);

    /**
     * 用户账号-新增
     * @param loginUser 当前登录用户
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealCreate(UserAccount loginUser,UserAccountVo userAccountVo) throws Exception ;

    /**
     * 用户账号-更新
     * @param loginUser 当前登录用户
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealUpdate(UserAccount loginUser,UserAccountVo userAccountVo) throws Exception ;

    /**
     * 用户账号-删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的用户id 集合
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception ;

    /**
     * 用户账号-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的用户id
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception ;



    /**
     * 用户账号-锁定
     * @param loginUser 当前登录用户
     * @param lockIds 要锁定的用户账号id 集合
     * @param isLock 是否锁定
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealBatchRenewLock(UserAccount loginUser,String[] lockIds,boolean isLock) throws Exception ;
    /**
     * 用户账号-锁定
     * @param loginUser 当前登录用户
     * @param lockId 要锁定的用户账号id
     * @param isLock 是否锁定
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealRenewLock(UserAccount loginUser,String lockId,boolean isLock) throws Exception ;


    /**
     * 用户分配角色
     * @param loginUser 当前登录用户
     * @param userAccountId 用户id
     * @param checkIds 角色id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealGrantRoleToUser(UserAccount loginUser,String userAccountId,String[] checkIds) throws Exception;

    /**
     * 用户分配职务
     * @param loginUser 当前登录用户
     * @param userAccountId 用户id
     * @param checkIds 职务id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     * @return
     */
    Integer dealGrantJobToUser(UserAccount loginUser,String userAccountId,String[] checkIds) throws Exception;


    /**
     * 用于保存前验证 数据库 中的唯一冲突
     * @param userAccountVo
     * @param wrapper
     * @return
     */
    boolean dealCheckDuplicateKey(UserAccountVo userAccountVo, QueryWrapper<UserAccount> wrapper);


    /**
     * 查询要导出的[用户账号] xlsModel 集合
     * @param loginUser 当前登录用户
     * @param checkIds
     * @param wrapper
     * @return
     */
    List<UserAccountXlsOutModel> dealGetExportXlsModelList(UserAccount loginUser,String[] checkIds, QueryWrapper<UserAccount> wrapper);


    /**
     * 查询所有已存在的 用户账号account
     * @param loginUser 当前登录用户
     * @param state
     * @param wrapper
     * @return
     */
    Set<String> dealGetExistAccountSet(UserAccount loginUser,Short state, QueryWrapper<UserAccount> wrapper);
}
