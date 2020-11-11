package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserAccountService extends IService<UserAccountEntity>, MyBaseMysqlService<UserAccountEntity, UserAccountMapper, UserAccountVo> {

    String FOREIGN_NAME_OF_USER_TENANT = "userTenant";
    String FOREIGN_NAME_OF_USER_DEPARTMENT = "userDepartment";


    /**
     * 根据LoginAccountDTO查询对应的用户账号entity
     * @param loginAccountDTO
     * @return
     */
    UserAccountEntity dealGetEntityByDTO(LoginAccountDTO loginAccountDTO);


    /**
     * 分页查询 用户列表
     * @param loginUserInfo              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserAccountVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccountEntity> paginationBean,
                                                         List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserAccountVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccountDto> paginationBean,
                                                      List<AntdvSortBean> sortBeans);

    /**
     * 用户账号-新增
     * @param loginUserInfo     当前登录用户
     * @param userAccountVo
     * @param loginUserInfo     当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, UserAccountVo userAccountVo) throws Exception;

    /**
     * 用户账号-更新
     * @param loginUserInfo     当前登录用户
     * @param userAccountVo
     * @param loginUserInfo     当前登录用户
     * @param loginUserInfo     当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, UserAccountVo userAccountVo) throws Exception;

    /**
     * 用户账号-锁定
     * @param loginUserInfo 当前登录用户
     * @param lockIds   要锁定的用户账号id 集合
     * @param isLock    是否锁定
     * @param loginUserInfo 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealBatchRenewLock(CurrentLoginUserInfo loginUserInfo, String[] lockIds, boolean isLock) throws Exception;

    /**
     * 用户账号-锁定
     * @param loginUserInfo 当前登录用户
     * @param lockId    要锁定的用户账号id
     * @param isLock    是否锁定
     * @param loginUserInfo 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealRenewLock(CurrentLoginUserInfo loginUserInfo, Long lockId, boolean isLock) throws Exception;


    /**
     * 用户分配角色
     * @param loginUserInfo     当前登录用户
     * @param userAccountId 用户id
     * @param checkIds      角色id集合
     * @param loginUserInfo     当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealGrantRoleToUser(CurrentLoginUserInfo loginUserInfo, Long userAccountId, Long[] checkIds) throws Exception;

    /**
     * 用户分配职务
     * @param loginUserInfo     当前登录用户
     * @param userAccountId 用户id
     * @param checkIds      职务id集合
     * @param loginUserInfo     当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealGrantJobToUser(CurrentLoginUserInfo loginUserInfo, Long userAccountId, Long[] checkIds) throws Exception;


    /**
     * 用于保存前验证 数据库 中的唯一冲突
     * @param userAccountVo
     * @param wrapper
     * @return
     */
    boolean dealCheckDuplicateKey(UserAccountVo userAccountVo, QueryWrapper<UserAccountEntity> wrapper);


    /**
     * 查询要导出的[用户账号] xlsModel 集合
     * @param loginUserInfo 当前登录用户
     * @param checkIds
     * @param wrapper
     * @return
     */
    List<UserAccountXlsOutModel> dealGetExportXlsModelList(CurrentLoginUserInfo loginUserInfo, Long[] checkIds, QueryWrapper<UserAccountEntity> wrapper);


    /**
     * 查询所有已存在的 用户账号account
     * @param loginUserInfo 当前登录用户
     * @param state
     * @param wrapper
     * @return
     */
    Set<String> dealGetExistAccountSet(CurrentLoginUserInfo loginUserInfo, Short state, QueryWrapper<UserAccountEntity> wrapper);

    /**
     * 配置缓存用-当前用户信息
     * @param userAccountId 用户id
     * @return
     */
    CurrentLoginUserInfo queryDbToCacheable(Long userAccountId);
}
