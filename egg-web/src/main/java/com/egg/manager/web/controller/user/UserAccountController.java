package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineRoleMapper;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineDepartmentTransfer;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineJobTransfer;
import com.egg.manager.persistence.em.define.pojo.transfer.DefinePermissionTransfer;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.transfer.DefineTenantTransfer;
import com.egg.manager.persistence.em.user.pojo.transfer.UserAccountTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Api(value = "API-用户账号接口")
@RestController
@RequestMapping("/user/userAccount")
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


    @RequiresRoles(value = {ShiroRoleConstant.ROOT, ShiroRoleConstant.SUPER_ROOT}, logical = Logical.OR)
    @PcWebOperationLog(fullPath = "/user/userAccount/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<UserAccountVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                      @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<UserAccountVo> result = MyCommonResult.gainQueryResult(UserAccountVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserAccountDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, UserAccountDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userAccountService.dealQueryPageByDtos(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<UserAccountVo> queryOneById(HttpServletRequest request, String accountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<UserAccountVo> result = MyCommonResult.gainQueryResult(UserAccountVo.class);
        try {
            UserAccountEntity account = userAccountMapper.selectById(accountId);
            UserAccountVo userAccountVo = UserAccountTransfer.transferEntityToVo(account);
            //取得 所属的 租户定义
            DefineTenantEntity belongTenant = defineTenantMapper.selectOneOfUserBelongTenant(account.getFid(), BaseStateEnum.ENABLED.getValue());
            if (belongTenant != null) {
                userAccountVo.setBelongTenantId(belongTenant.getFid());
                userAccountVo.setBelongTenant(DefineTenantTransfer.transferEntityToVo(belongTenant));
            }
            //取得 所属的 部门定义
            DefineDepartmentEntity belongDepartment = defineDepartmentMapper.selectOneOfUserBelongDepartment(account.getFid(), BaseStateEnum.ENABLED.getValue());
            if (belongDepartment != null) {
                userAccountVo.setBelongDepartmentId(belongDepartment.getFid());
                userAccountVo.setBelongDepartment(DefineDepartmentTransfer.transferEntityToVo(belongDepartment));
            }
            result.setBean(userAccountVo);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedRole")
    @PostMapping(value = "/gainGrantedRole")
    public MyCommonResult<DefineRoleVo> gainGrantedRole(HttpServletRequest request, Long userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<DefineRoleVo> result = MyCommonResult.gainQueryResult(DefineRoleVo.class);
        try {
            List<DefineRoleEntity> defineRoleEntityList = defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineRoleTransfer.transferEntityToVoList(defineRoleEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->权限定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedPermission")
    @PostMapping(value = "/gainGrantedPermission")
    public MyCommonResult<DefinePermissionVo> gainGrantedPermission(HttpServletRequest request, Long userAccountId,
                                                                    @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class);
        try {
            List<DefinePermissionEntity> definePermissionEntityList = definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
            result.setResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissionEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询已获授权/用户账号->职务定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedJob")
    @PostMapping(value = "/gainGrantedJob")
    public MyCommonResult<DefineJobVo> gainGrantedJob(HttpServletRequest request, Long userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<DefineJobVo> result = MyCommonResult.gainQueryResult(DefineJobVo.class);
        try {
            List<DefineJobEntity> defineJobEntityList = defineJobMapper.findAllJobByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
            Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userAccountService.dealCreate(loginUserInfo, userAccountVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer changeCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
            Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = userAccountService.dealUpdate(loginUserInfo, userAccountVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/userAccount/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            //批量伪删除
            delCount = userAccountService.dealBatchLogicDelete(loginUserInfo, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/userAccount/deleteById")
    @ApiOperation(value = "伪删除->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = userAccountService.dealLogicDeleteById(loginUserInfo, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新/批量修改状态->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/batchUpdateLockByIds")
    @PostMapping(value = "/batchUpdateLockByIds")
    public MyCommonResult batchUpdateLockByIds(HttpServletRequest request, String[] lockIds, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer lockCount = 0;
        try {
            Assert.notEmpty(lockIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            //批量伪删除
            lockCount = userAccountService.dealBatchRenewLock(loginUserInfo, lockIds, lockFlag);
            result.setCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新/修改状态->用户账号", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateLockById")
    @PostMapping(value = "/updateLockById")
    public MyCommonResult updateLockById(HttpServletRequest request, Long lockId, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer lockCount = 0;
        try {
            Assert.notNull(lockId, BaseRstMsgConstant.ErrorMsg.unknowId());
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            lockCount = userAccountService.dealRenewLock(loginUserInfo, lockId, lockFlag);
            result.setCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "授权/用户账号->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantRoleToUser")
    @PostMapping(value = "/grantRoleToUser")
    public MyCommonResult doGrantRoleToUser(HttpServletRequest request, Long userAccountId, Long[] checkIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notNull(userAccountId, "未知用户id:" + actionFailMsg);
            Integer grantCount = userAccountService.dealGrantRoleToUser(loginUserInfo, userAccountId, checkIds);
            result.setCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "授权/用户账号->职务定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantJobToUser")
    @PostMapping(value = "/grantJobToUser")
    public MyCommonResult doGrantJobToUser(HttpServletRequest request, Long userAccountId, Long[] checkIds,
                                           @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notNull(userAccountId, "未知用户id:" + actionFailMsg);
            Integer grantCount = userAccountService.dealGrantJobToUser(loginUserInfo, userAccountId, checkIds);
            result.setCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
