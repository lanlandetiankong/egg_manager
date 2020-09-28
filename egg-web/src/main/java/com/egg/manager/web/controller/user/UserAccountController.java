package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.constants.funcModule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.controllers.user.UserAccountFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(value = "API ==>>  UserAccountController ", description = "用户账号接口")
@RestController
@RequestMapping("/user/user_account")
public class UserAccountController extends BaseController {

    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private DefineRoleMapper defineRoleMapper;
    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private DefineJobMapper defineJobMapper;
    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;

    @Autowired
    private UserAccountService userAccountService;


    @RequiresRoles(value = {"Root", "SuperRoot"}, logical = Logical.OR)
    @PcWebOperationLog(action = "查询用户信息-Dto列表", description = "查询用户信息-Dto列表", fullPath = "/user/user_account/getAllUserAccountDtos")
    @ApiOperation(value = "查询用户信息-Dto列表", notes = "查询用户信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllUserAccountDtos")
    public MyCommonResult<UserAccountVo> doGetAllUserAccountDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                 @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserAccountVo> result = MyCommonResult.gainQueryResult(UserAccountVo.class, UserAccountFuncModuleConstant.Success.queryPage);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserAccountDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,UserAccountDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userAccountService.dealQueryPageByDtos(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }


    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户信息", description = "根据用户id查询用户信息", fullPath = "/user/user_account/getUserAccountById")
    @PostMapping(value = "/getUserAccountById")
    public MyCommonResult<UserAccountVo> doGetUserAccountById(HttpServletRequest request, String accountId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserAccountVo> result = MyCommonResult.gainQueryResult(UserAccountVo.class, UserAccountFuncModuleConstant.Success.queryPage);
        try {
            UserAccount account = userAccountMapper.selectById(accountId);
            UserAccountVo userAccountVo = UserAccountTransfer.transferEntityToVo(account);
            //取得 所属的 租户定义
            DefineTenant belongTenant = defineTenantMapper.selectOneOfUserBelongTenant(account.getFid(), BaseStateEnum.ENABLED.getValue());
            if (belongTenant != null) {
                userAccountVo.setBelongTenantId(belongTenant.getFid());
                userAccountVo.setBelongTenant(DefineTenantTransfer.transferEntityToVo(belongTenant));
            }
            //取得 所属的 部门定义
            DefineDepartment belongDepartment = defineDepartmentMapper.selectOneOfUserBelongDepartment(account.getFid(), BaseStateEnum.ENABLED.getValue());
            if (belongDepartment != null) {
                userAccountVo.setBelongDepartmentId(belongDepartment.getFid());
                userAccountVo.setBelongDepartment(DefineDepartmentTransfer.transferEntityToVo(belongDepartment));
            }
            result.setBean(userAccountVo);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }

    @ApiOperation(value = "查询用户所拥有的角色", notes = "根据用户id查询用户已有的角色", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户所拥有的角色", description = "根据用户id查询用户已有的角色", fullPath = "/user/user_account/getAllRoleByUserAccountId")
    @PostMapping(value = "/getAllRoleByUserAccountId")
    public MyCommonResult<DefineRoleVo> doGetAllRoleByUserAccountId(HttpServletRequest request, String userAccountId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = MyCommonResult.gainQueryResult(DefineRoleVo.class, UserAccountFuncModuleConstant.Success.queryGranted);
        try {
            List<DefineRole> defineRoleList = defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineRoleTransfer.transferEntityToVoList(defineRoleList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.queryGranted);
        }
        return result;
    }

    @ApiOperation(value = "查询用户所拥有的权限", notes = "根据用户id查询用户已有的权限", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户所拥有的权限", description = "根据用户id查询用户已有的权限", fullPath = "/user/user_account/getAllPermissionByUserAccountId")
    @PostMapping(value = "/getAllPermissionByUserAccountId")
    public MyCommonResult<DefinePermissionVo> doGetAllPermissionByUserAccountId(HttpServletRequest request, String userAccountId,
                                                                                @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class, UserAccountFuncModuleConstant.Success.queryGranted);
        try {
            List<DefinePermission> definePermissionList = definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
            result.setResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissionList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.queryGranted);
        }
        return result;
    }


    @ApiOperation(value = "查询用户所拥有的职务", notes = "根据用户id查询用户已有的职务", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户所拥有的职务", description = "根据用户id查询用户已有的职务", fullPath = "/user/user_account/getAllJobByUserAccountId")
    @PostMapping(value = "/getAllJobByUserAccountId")
    public MyCommonResult<DefineJobVo> doGetAllJobByUserAccountId(HttpServletRequest request, String userAccountId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = MyCommonResult.gainQueryResult(DefineJobVo.class, UserAccountFuncModuleConstant.Success.queryGranted);
        try {
            List<DefineJob> defineJobList = defineJobMapper.findAllJobByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.queryGranted);
        }
        return result;
    }


    @ApiOperation(value = "新增用户", notes = "表单方式新增用户", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增用户", description = "表单方式新增用户", fullPath = "/user/user_account/doAddUserAccount")
    @PostMapping(value = "/doAddUserAccount")
    public MyCommonResult doAddUserAccount(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.create);
        Integer addCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
            Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userAccountService.dealCreate(loginUser, userAccountVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.create);
        }
        return result;
    }


    @ApiOperation(value = "更新用户信息", notes = "表单方式更新用户信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新用户信息", description = "表单方式更新用户信息", fullPath = "/user/user_account/doUpdateUserAccount")
    @PostMapping(value = "/doUpdateUserAccount")
    public MyCommonResult doUpdateUserAccount(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.update);
        Integer changeCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
            Assert.notNull(userAccountVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = userAccountService.dealUpdate(loginUser, userAccountVo, false);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.update);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除用户", description = "根据用户id批量删除用户", fullPath = "/user/user_account/batchDelUserAccountByIds")
    @ApiOperation(value = "批量删除用户", notes = "根据用户id批量删除用户", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的用户id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelUserAccountByIds")
    public MyCommonResult doBatchDeleteUserAccountById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.batchDeleteByIds);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            //批量伪删除
            delCount = userAccountService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.batchDeleteByIds);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除用户", description = "根据用户id删除用户", fullPath = "/user/user_account/delOneUserAccountById")
    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的用户id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneUserAccountById")
    public MyCommonResult doDelOneUserAccountById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.deleteById);
        Integer delCount = 0;
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = userAccountService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.deleteById);
        }
        return result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id批量锁定或解锁用户", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "修改用户锁定状态", description = "根据用户id批量锁定或解锁用户", fullPath = "/user/user_account/batchLockUserAccountByIds")
    @PostMapping(value = "/batchLockUserAccountByIds")
    public MyCommonResult doBatchLockUserAccountById(HttpServletRequest request, String[] lockIds, Boolean lockFlag, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.updateState);
        Integer lockCount = 0;
        try {
            Assert.notEmpty(lockIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            //批量伪删除
            lockCount = userAccountService.dealBatchRenewLock(loginUser, lockIds, lockFlag);
            result.setCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.updateState);
        }
        return result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id锁定或解锁用户", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "修改用户锁定状态", description = "根据用户id锁定或解锁用户", fullPath = "/user/user_account/lockOneUserAccountById")
    @PostMapping(value = "/lockOneUserAccountById")
    public MyCommonResult doLockOneUserAccountById(HttpServletRequest request, String lockId, Boolean lockFlag, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.updateState);
        Integer lockCount = 0;
        try {
            Assert.notBlank(lockId,BaseRstMsgConstant.ErrorMsg.unknowId());
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            lockCount = userAccountService.dealRenewLock(loginUser, lockId, lockFlag);
            result.setCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.updateState);
        }
        return result;
    }


    @ApiOperation(value = "用户分配角色", notes = "为用户分配角色", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "用户分配角色", description = "为用户分配角色", fullPath = "/user/user_account/grantRoleToUser")
    @PostMapping(value = "/grantRoleToUser")
    public MyCommonResult doGrantRoleToUser(HttpServletRequest request, String userAccountId, String[] checkIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.grantOper);
        try {
            Assert.notBlank(userAccountId,"未知用户id:"+actionFailMsg);
            Integer grantCount = userAccountService.dealGrantRoleToUser(loginUser, userAccountId, checkIds);
            result.setCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.grantOper);
        }
        return result;
    }


    @ApiOperation(value = "用户分配职务", notes = "为用户分配职务", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "用户分配职务", description = "为用户分配职务", fullPath = "/user/user_account/grantJobToUser")
    @PostMapping(value = "/grantJobToUser")
    public MyCommonResult doGrantJobToUser(HttpServletRequest request, String userAccountId, String[] checkIds,
                                           @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserAccountFuncModuleConstant.Success.grantOper);
        try {
            Assert.notBlank(userAccountId,"未知用户id:"+actionFailMsg);
            Integer grantCount = userAccountService.dealGrantJobToUser(loginUser, userAccountId, checkIds);
            result.setCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountFuncModuleConstant.Failure.grantOper);
        }
        return result;
    }
}
