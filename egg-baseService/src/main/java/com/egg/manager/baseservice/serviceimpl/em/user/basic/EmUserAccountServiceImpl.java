package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.helper.PasswordHelper;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.facade.api.services.em.user.basic.EmUserGroupService;
import com.egg.manager.facade.api.services.em.user.basic.EmUserJobService;
import com.egg.manager.facade.api.services.em.user.basic.EmUserRoleService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.*;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.*;
import com.egg.manager.facade.persistence.em.user.domain.constant.UserAccountConstant;
import com.egg.manager.facade.persistence.commons.base.constant.db.redis.RedisShiroKeyConstant;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.facade.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.facade.persistence.em.user.domain.enums.UserAccountStateEnum;
import com.egg.manager.facade.persistence.commons.base.exception.BusinessException;
import com.egg.manager.facade.persistence.commons.base.exception.MyDbException;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.facade.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.facade.persistence.em.user.pojo.excel.export.user.EmUserAccountXlsOutModel;
import com.egg.manager.facade.persistence.em.user.pojo.initialize.EmUserDepartmentPojoInitialize;
import com.egg.manager.facade.persistence.em.user.pojo.initialize.EmUserJobPojoInitialize;
import com.egg.manager.facade.persistence.em.user.pojo.initialize.EmUserRolePojoInitialize;
import com.egg.manager.facade.persistence.em.user.pojo.initialize.EmUserTenantPojoInitialize;
import com.egg.manager.facade.persistence.em.user.pojo.transfer.EmUserAccountTransfer;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAccountVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmUserAccountService.class)
public class EmUserAccountServiceImpl extends MyBaseMysqlServiceImpl<EmUserAccountMapper, EmUserAccountEntity, EmUserAccountVo> implements EmUserAccountService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;
    @Autowired
    private EmUserRoleMapper emUserRoleMapper;
    @Autowired
    private EmUserJobMapper emUserJobMapper;
    @Autowired
    private EmUserTenantMapper emUserTenantMapper;
    @Autowired
    private EmUserDepartmentMapper emUserDepartmentMapper;

    @Reference
    private EmUserJobService emUserJobService;
    @Reference
    private EmUserGroupService emUserGroupService;
    @Reference
    private EmUserRoleService emUserRoleService;

    @Override
    public EmUserAccountEntity dealGetEntityByDTO(LoginAccountDTO loginAccountDTO) {
        QueryWrapper<EmUserAccountEntity> wrapper = new QueryWrapper<EmUserAccountEntity>();
        wrapper.setEntity(new EmUserAccountEntity());
        wrapper.eq("account", loginAccountDTO.getAccount())
                .eq(FieldConst.COL_STATE, UserAccountStateEnum.ENABLED.getValue());
        return emUserAccountMapper.selectOne(wrapper);
    }


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAccountEntity> queryPage) {
        //解析 搜索条件
        QueryWrapper<EmUserAccountEntity> userAccountEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPage);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPage.getPageConf());
        //取得 总数
        Integer total = emUserAccountMapper.selectCount(userAccountEntityWrapper);
        result.settingPage(queryPage.getPageConf(), Long.valueOf(total));
        IPage iPage = emUserAccountMapper.selectPage(page, userAccountEntityWrapper);
        List<EmUserAccountEntity> userAccountEntities = iPage.getRecords();
        result.putGridList(EmUserAccountTransfer.transferEntityToVoList(userAccountEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAccountDto> queryPage) {
        Page<EmUserAccountDto> mpPagination = queryPage.toMpPage();
        QueryFieldArr queryFieldListTemp = new QueryFieldArr();
        //用户与租户关联 的外表-搜索条件
        QueryFieldArr queryTenantFieldBeanList = new QueryFieldArr();
        //用户与部门关联 的外表-搜索条件
        QueryFieldArr queryDepartmentFieldBeanList = new QueryFieldArr();
        for (QueryField queryField : queryPage.getQuery()) {
            //外部关联名，条件需单独识别
            String foreignName = queryField.getForeignName();
            if (StringUtils.isBlank(foreignName)) {
                queryFieldListTemp.add(queryField);
            } else {
                //foreignName匹配一致的会被认定为 指定表的 搜索条件
                if (FOREIGN_NAME_OF_USER_TENANT.equals(foreignName)) {
                    //筛选-所属租户
                    queryTenantFieldBeanList.add(queryField);
                } else if (FOREIGN_NAME_OF_USER_DEPARTMENT.equals(foreignName)) {
                    //筛选-所属部门
                    queryDepartmentFieldBeanList.add(queryField);
                }
            }
        }
        List<EmUserAccountDto> emUserAccountDtoList = emUserAccountMapper.selectQueryPage(mpPagination, queryFieldListTemp, queryPage.getSortMap(), queryTenantFieldBeanList, queryDepartmentFieldBeanList);
        result.settingPage(queryPage,mpPagination);
        result.putGridList(EmUserAccountTransfer.transferDtoToVoList(emUserAccountDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmUserAccountVo emUserAccountVo) throws Exception {
        if (this.dealCheckDuplicateKey(emUserAccountVo, new QueryWrapper<>())) {
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        EmUserAccountEntity emUserAccountEntity = EmUserAccountTransfer.transferVoToEntity(emUserAccountVo);
        emUserAccountEntity = super.doBeforeCreate(loginUserInfo, emUserAccountEntity);
        //密码-操作助手
        PasswordHelper passwordHelper = new PasswordHelper();
        //盐&md5密码加密
        passwordHelper.encryptPassword(emUserAccountEntity);
        if (null == emUserAccountVo.getLocked()) {
            //如果没设置值，默认不锁定
            emUserAccountEntity.setLocked(SwitchStateEnum.Close.getValue());
        }
        emUserAccountEntity.setUserType(UserAccountBaseTypeEnum.SimpleUser.getValue());
        if (StringUtils.isBlank(emUserAccountVo.getPassword())) {
            String pwd = UserAccountConstant.DEFAULT_PWD;
            emUserAccountVo.setPassword(pwd);
            emUserAccountEntity.setPassword(pwd);
        }
        Integer addCount = emUserAccountMapper.insert(emUserAccountEntity);
        //关联 租户
        if (StringUtils.isNotBlank(emUserAccountEntity.getFid()) && StringUtils.isNotBlank(emUserAccountVo.getBelongTenantId())) {
            EmUserTenantEntity emUserTenantEntity = EmUserTenantPojoInitialize.generateSimpleInsertEntity(emUserAccountEntity.getFid(), emUserAccountVo.getBelongTenantId(), loginUserInfo);
            emUserTenantMapper.insert(emUserTenantEntity);
        } else {
            throw new BusinessException(BaseRstMsgConstant.ErrorMsg.createActionFail());
        }

        //关联 部门
        if (StringUtils.isNotBlank(emUserAccountEntity.getFid()) && StringUtils.isNotBlank(emUserAccountVo.getBelongDepartmentId())) {
            EmUserDepartmentEntity emUserDepartmentEntity = EmUserDepartmentPojoInitialize.generateSimpleInsertEntity(emUserAccountEntity.getFid(), emUserAccountVo.getBelongDepartmentId(), loginUserInfo);
            emUserDepartmentMapper.insert(emUserDepartmentEntity);
        } else {
            throw new BusinessException(BaseRstMsgConstant.ErrorMsg.createActionFail());
        }
        return addCount;
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmUserAccountVo emUserAccountVo) throws Exception {
        QueryWrapper<EmUserAccountEntity> uniWrapper = new QueryWrapper<EmUserAccountEntity>()
                .ne(FieldConst.COL_FID, emUserAccountVo.getFid());
        if (dealCheckDuplicateKey(emUserAccountVo, uniWrapper)) {
            //已有重复键值
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Integer changeCount = 0;
        EmUserAccountEntity emUserAccountEntity = EmUserAccountTransfer.transferVoToEntity(emUserAccountVo);
        emUserAccountEntity = super.doBeforeUpdate(loginUserInfo, emUserAccountEntity);
        changeCount = emUserAccountMapper.updateById(emUserAccountEntity);
        //关联 租户
        if (StringUtils.isNotBlank(emUserAccountEntity.getFid()) && StringUtils.isNotBlank(emUserAccountVo.getBelongTenantId())) {
            QueryWrapper<EmUserTenantEntity> tenantQueryWrapper = new QueryWrapper<EmUserTenantEntity>();
            tenantQueryWrapper.eq("user_account_id", emUserAccountEntity.getFid())
                    .eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
            EmUserTenantEntity emUserTenantEntity = emUserTenantMapper.selectOne(tenantQueryWrapper);
            if (emUserTenantEntity == null) {
                emUserTenantEntity = EmUserTenantPojoInitialize.generateSimpleInsertEntity(emUserAccountEntity.getFid(), emUserAccountVo.getBelongTenantId(), loginUserInfo);
                emUserTenantMapper.insert(emUserTenantEntity);
            } else {
                emUserTenantEntity.setDefineTenantId(emUserAccountVo.getBelongTenantId());
                emUserTenantMapper.updateById(emUserTenantEntity);
            }
        } else {
            throw new BusinessException(BaseRstMsgConstant.ErrorMsg.updateActionFail());
        }
        //关联 部门
        if (StringUtils.isNotBlank(emUserAccountEntity.getFid()) && StringUtils.isNotBlank(emUserAccountVo.getBelongDepartmentId())) {
            QueryWrapper<EmUserDepartmentEntity> departmentQueryWrapper = new QueryWrapper<EmUserDepartmentEntity>();
            departmentQueryWrapper.eq("user_account_id", emUserAccountEntity.getFid())
                    .eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
            EmUserDepartmentEntity emUserDepartmentEntity = emUserDepartmentMapper.selectOne(departmentQueryWrapper);
            if (emUserDepartmentEntity == null) {
                emUserDepartmentEntity = EmUserDepartmentPojoInitialize.generateSimpleInsertEntity(emUserAccountEntity.getFid(), emUserAccountVo.getBelongDepartmentId(), loginUserInfo);
                emUserDepartmentMapper.insert(emUserDepartmentEntity);
            } else {
                emUserDepartmentEntity.setDefineDepartmentId(emUserAccountVo.getBelongDepartmentId());
                emUserDepartmentMapper.updateById(emUserDepartmentEntity);
            }
        } else {
            throw new BusinessException(BaseRstMsgConstant.ErrorMsg.updateActionFail());
        }
        return changeCount;
    }


    @Override
    public Integer dealBatchRenewLock(CurrentLoginEmUserInfo loginUserInfo, String[] lockIds, boolean isLock) throws Exception {
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        Integer lockCount = 0;
        if (lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Lists.newArrayList(lockIds);
            //批量设置为 锁定
            lockCount = emUserAccountMapper.batchLockUserByIds(lockIdList, lockState, loginUserInfo);
        }
        return lockCount;
    }


    @Override
    public Integer dealRenewLock(CurrentLoginEmUserInfo loginUserInfo, String lockId, boolean isLock) throws Exception {
        Short lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        EmUserAccountEntity emUserAccountEntity = EmUserAccountEntity.builder().fid(lockId).locked(lockState).build();
        if (loginUserInfo != null) {
            emUserAccountEntity.setLastModifyerId(loginUserInfo.getFid());
        }
        Integer lockCount = emUserAccountMapper.updateById(emUserAccountEntity);
        return lockCount;
    }

    @Override
    public Integer dealGrantRoleToUser(CurrentLoginEmUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUserInfo != null ? loginUserInfo.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = emUserAccountMapper.clearAllRoleByUserId(userAccountId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的角色id 集合
            List<String> oldCheckRoleIds = emUserRoleMapper.findAllRoleIdByUserAccountId(userAccountId, false);
            if (CollectionUtil.isEmpty(oldCheckRoleIds)) {
                List<EmUserRoleEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(EmUserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                }
                //批量新增行
                emUserRoleService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
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
                if (CollectionUtil.isNotEmpty(enableIds)) {
                    //批量启用
                    emUserRoleMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    emUserRoleMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    List<EmUserRoleEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(EmUserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    emUserRoleService.saveBatch(addEntitys);
                }
            }
        }

        return changeCount;
    }


    @Override
    public Integer dealGrantJobToUser(CurrentLoginEmUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUserInfo != null ? loginUserInfo.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = emUserAccountMapper.clearAllJobByUserId(userAccountId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的职务id 集合
            List<String> oldCheckJobIds = emUserJobMapper.findAllJobIdByUserAccountId(userAccountId, false);
            if (CollectionUtil.isEmpty(oldCheckJobIds)) {
                List<EmUserJobEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(EmUserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                }
                //批量新增行
                emUserJobService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
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
                if (CollectionUtil.isNotEmpty(enableIds)) {
                    //批量启用
                    emUserJobMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    emUserJobMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    //批量新增行
                    List<EmUserJobEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(EmUserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    emUserJobService.saveBatch(addEntitys);
                }
            }
        }
        return changeCount;
    }


    @Override
    public boolean dealCheckDuplicateKey(EmUserAccountVo emUserAccountVo, QueryWrapper<EmUserAccountEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("account", emUserAccountVo.getAccount());
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        return emUserAccountMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<EmUserAccountXlsOutModel> dealGetExportXlsModelList(CurrentLoginEmUserInfo loginUserInfo, String[] checkIds, QueryWrapper<EmUserAccountEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.in(checkIds != null, FieldConst.COL_FID, checkIds);
        return EmUserAccountTransfer.entityListToXlsOutModels(emUserAccountMapper.selectList(wrapper));
    }


    @Override
    public Set<String> dealGetExistAccountSet(CurrentLoginEmUserInfo loginUserInfo, Short state, QueryWrapper<EmUserAccountEntity> wrapper) {
        Set<String> accountSet = new HashSet<>();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq(state != null, FieldConst.COL_STATE, state);
        List<EmUserAccountEntity> emUserAccountEntityList = emUserAccountMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(emUserAccountEntityList)) {
            for (EmUserAccountEntity user : emUserAccountEntityList) {
                accountSet.add(user.getAccount());
            }
        }
        return accountSet;
    }

    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_ACCOUNT, key = "#userAccountId", condition = "#userAccountId!=null")
    public CurrentLoginEmUserInfo queryDbToCacheable(String userAccountId) {
        if (userAccountId == null) {
            return null;
        }
        //用户信息
        EmUserAccountEntity emUserAccountEntity = emUserAccountMapper.selectById(userAccountId);
        //用户所属职务
        List<EmDefineJobEntity> belongJobs = emUserJobService.queryAllUserBelong(userAccountId);
        List<EmDefineGroupEntity> belongGroups = emUserGroupService.queryAllUserBelong(userAccountId);
        CurrentLoginEmUserInfo loginUserInfo = CurrentLoginEmUserInfo.transferFromEntity(emUserAccountEntity);
        loginUserInfo.setBelongJobList(belongJobs);
        loginUserInfo.setBelongGroupList(belongGroups);
        return loginUserInfo;
    }

    @Override
    public boolean reflushSecurePwd(CurrentLoginEmUserInfo loginUserInfo) {
        QueryWrapper<EmUserAccountEntity> query = new QueryWrapper<>();
        query.isNotNull("password");
        List<EmUserAccountEntity> userAccountEntities = emUserAccountMapper.selectList(query);
        PasswordHelper passwordHelper = new PasswordHelper();
        for (EmUserAccountEntity emUserAccountEntity : userAccountEntities) {
            passwordHelper.encryptPassword(emUserAccountEntity);
        }
        this.updateBatchById(userAccountEntities);
        return true;
    }
}
