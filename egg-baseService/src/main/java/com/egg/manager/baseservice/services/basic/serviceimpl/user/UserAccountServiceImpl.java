package com.egg.manager.baseservice.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.constant.define.UserAccountConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.*;
import com.egg.manager.persistence.db.mysql.mapper.user.*;
import com.egg.manager.persistence.pojo.common.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.initialize.user.UserDepartmentPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.initialize.user.UserJobPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.initialize.user.UserRolePojoInitialize;
import com.egg.manager.persistence.pojo.mysql.initialize.user.UserTenantPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserAccountService.class)
public class UserAccountServiceImpl extends MyBaseMysqlServiceImpl<UserAccountMapper, UserAccount, UserAccountVo> implements UserAccountService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserJobMapper userJobMapper;
    @Autowired
    private UserTenantMapper userTenantMapper;
    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Override
    public UserAccount dealGetEntityByDTO(LoginAccountDTO loginAccountDTO) {
        QueryWrapper<UserAccount> wrapper = new QueryWrapper<UserAccount>();
        wrapper.setEntity(new UserAccount());
        wrapper.eq("account", loginAccountDTO.getAccount())
                .eq("state", UserAccountStateEnum.ENABLED.getValue());
        return userAccountMapper.selectOne(wrapper);
    }


    @Override
    public MyCommonResult<UserAccountVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccount> paginationBean,
                                                                List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserAccount> userAccountEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userAccountMapper.selectPage(page, userAccountEntityWrapper);
        List<UserAccount> userAccounts = iPage.getRecords();
        result.setResultList(UserAccountTransfer.transferEntityToVoList(userAccounts));
        return result;
    }

    @Override
    public MyCommonResult<UserAccountVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserAccountDto> paginationBean,
                                                             List<AntdvSortBean> sortBeans) {
        Page<UserAccountDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<QueryFormFieldBean> queryFieldBeanListTemp = new ArrayList<QueryFormFieldBean>();
        //用户与租户关联 的外表-搜索条件
        List<QueryFormFieldBean> queryTenantFieldBeanList = new ArrayList<QueryFormFieldBean>();
        //用户与部门关联 的外表-搜索条件
        List<QueryFormFieldBean> queryDepartmentFieldBeanList = new ArrayList<QueryFormFieldBean>();
        for (QueryFormFieldBean queryFormFieldBean : queryFieldBeanList) {
            //外部关联名，条件需单独识别
            String foreignName = queryFormFieldBean.getForeignName();
            if (StringUtils.isBlank(foreignName)) {
                queryFieldBeanListTemp.add(queryFormFieldBean);
            } else {
                //foreignName匹配一致的会被认定为 指定表的 搜索条件
                if (FOREIGN_NAME_OF_USER_TENANT.equals(foreignName)) {
                    //筛选-所属租户
                    queryTenantFieldBeanList.add(queryFormFieldBean);
                } else if (FOREIGN_NAME_OF_USER_DEPARTMENT.equals(foreignName)) {
                    //筛选-所属部门
                    queryDepartmentFieldBeanList.add(queryFormFieldBean);
                }
            }
        }
        List<UserAccountDto> userAccountDtoList = userAccountMapper.selectQueryPage(mpPagination, queryFieldBeanListTemp, sortBeans, queryTenantFieldBeanList, queryDepartmentFieldBeanList);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserAccountTransfer.transferDtoToVoList(userAccountDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, UserAccountVo userAccountVo) throws Exception {
        if (this.dealCheckDuplicateKey(userAccountVo, new QueryWrapper<>())) {
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        UserAccount userAccount = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccount = super.doBeforeCreate(loginUser, userAccount, true);
        if (null == userAccountVo.getLocked()) {
            //如果没设置值，默认不锁定
            userAccount.setLocked(SwitchStateEnum.Close.getValue());
        }
        userAccount.setUserType(UserAccountBaseTypeEnum.SimpleUser.getValue());
        if (StringUtils.isBlank(userAccountVo.getPassword())) {
            String pwd = UserAccountConstant.DEFAULT_PWD;
            userAccountVo.setPassword(pwd);
            userAccount.setPassword(pwd);
        }
        Integer addCount = userAccountMapper.insert(userAccount);
        //关联 租户
        if (StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            UserTenant userTenant = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccount.getFid(), userAccountVo.getBelongTenantId(), loginUser);
            userTenantMapper.insert(userTenant);
        } else {
            throw new BusinessException("关联用户与租户失败！创建用户失败！");
        }

        //关联 部门
        if (StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            UserDepartment userDepartment = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccount.getFid(), userAccountVo.getBelongDepartmentId(), loginUser);
            userDepartmentMapper.insert(userDepartment);
        } else {
            throw new BusinessException("关联用户与部门失败！创建用户失败！");
        }
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, UserAccountVo userAccountVo) throws Exception {
        QueryWrapper<UserAccount> uniWrapper = new QueryWrapper<UserAccount>()
                .ne("fid", userAccountVo.getFid());
        if (dealCheckDuplicateKey(userAccountVo, uniWrapper)) {
            //已有重复键值
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Integer changeCount = 0;
        UserAccount userAccount = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccount = super.doBeforeUpdate(loginUser, userAccount);
        changeCount = userAccountMapper.updateById(userAccount);
        //关联 租户
        if (StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            QueryWrapper<UserTenant> tenantQueryWrapper = new QueryWrapper<UserTenant>();
            tenantQueryWrapper.eq("user_account_id", userAccount.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserTenant userTenant = userTenantMapper.selectOne(tenantQueryWrapper);
            if (userTenant == null) {
                userTenant = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccount.getFid(), userAccountVo.getBelongTenantId(), loginUser);
                userTenantMapper.insert(userTenant);
            } else {
                userTenant.setDefineTenantId(userAccountVo.getBelongTenantId());
                userTenantMapper.updateById(userTenant);
            }
        } else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！");
        }
        //关联 部门
        if (StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            QueryWrapper<UserDepartment> departmentQueryWrapper = new QueryWrapper<UserDepartment>();
            departmentQueryWrapper.eq("user_account_id", userAccount.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserDepartment userDepartment = userDepartmentMapper.selectOne(departmentQueryWrapper);
            if (userDepartment == null) {
                userDepartment = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccount.getFid(), userAccountVo.getBelongDepartmentId(), loginUser);
                userDepartmentMapper.insert(userDepartment);
            } else {
                userDepartment.setDefineDepartmentId(userAccountVo.getBelongDepartmentId());
                userDepartmentMapper.updateById(userDepartment);
            }
        } else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！");
        }
        return changeCount;
    }


    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = userAccountMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        UserAccount userAccount = super.doBeforeDeleteOneById(loginUser, UserAccount.class, delId);
        return userAccountMapper.updateById(userAccount);
    }


    @Override
    public Integer dealBatchRenewLock(UserAccount loginUser, String[] lockIds, boolean isLock) throws Exception {
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        Integer lockCount = 0;
        if (lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Arrays.asList(lockIds);
            //批量设置为 锁定
            lockCount = userAccountMapper.batchLockUserByIds(lockIdList, lockState, loginUser);
        }
        return lockCount;
    }


    @Override
    public Integer dealRenewLock(UserAccount loginUser, String lockId, boolean isLock) throws Exception {
        Short lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        UserAccount userAccount = UserAccount.builder().fid(lockId).locked(lockState).build();
        if (loginUser != null) {
            userAccount.setLastModifyerId(loginUser.getFid());
        }
        Integer lockCount = userAccountMapper.updateById(userAccount);
        return lockCount;
    }

    @Override
    public Integer dealGrantRoleToUser(UserAccount loginUser, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUser != null ? loginUser.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllRoleByUserId(userAccountId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的角色id 集合
            List<String> oldCheckRoleIds = userRoleMapper.findAllRoleIdByUserAccountId(userAccountId, false);
            if (oldCheckRoleIds == null || oldCheckRoleIds.isEmpty()) {
                List<UserRole> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                }
                //批量新增行
                userRoleMapper.customBatchInsert(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckRoleIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (enableIds.isEmpty() == false) {
                    //批量启用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    List<UserRole> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                    }
                    //批量新增行
                    userRoleMapper.customBatchInsert(addEntitys);
                }
            }
        }

        return changeCount;
    }


    @Override
    public Integer dealGrantJobToUser(UserAccount loginUser, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUser != null ? loginUser.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllJobByUserId(userAccountId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的职务id 集合
            List<String> oldCheckJobIds = userJobMapper.findAllJobIdByUserAccountId(userAccountId, false);
            if (oldCheckJobIds == null || oldCheckJobIds.isEmpty()) {
                List<UserJob> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                }
                //批量新增行
                userJobMapper.customBatchInsert(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckJobIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (enableIds.isEmpty() == false) {
                    //批量启用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserJob> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                    }
                    //批量新增行
                    userJobMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount;
    }


    @Override
    public boolean dealCheckDuplicateKey(UserAccountVo userAccountVo, QueryWrapper<UserAccount> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("account", userAccountVo.getAccount());
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        return userAccountMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<UserAccountXlsOutModel> dealGetExportXlsModelList(UserAccount loginUser, String[] checkIds, QueryWrapper<UserAccount> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        if (checkIds != null) {
            wrapper.in("fid", checkIds);
        }
        return UserAccountTransfer.entityListToXlsOutModels(userAccountMapper.selectList(wrapper));
    }


    @Override
    public Set<String> dealGetExistAccountSet(UserAccount loginUser, Short state, QueryWrapper<UserAccount> wrapper) {
        Set<String> accountSet = new HashSet<>();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        if (state != null) {
            wrapper.eq("state", state);
        }
        List<UserAccount> userAccountList = userAccountMapper.selectList(wrapper);
        if (userAccountList != null && userAccountList.isEmpty() == false) {
            for (UserAccount user : userAccountList) {
                accountSet.add(user.getAccount());
            }
        }
        return accountSet;
    }

}
