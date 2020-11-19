package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
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
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineTenantTransfer;
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
import org.springframework.web.bind.annotation.GetMapping;
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
    @ApiOperation(value = "分页查询(dto)->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryField> queryFieldList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldList.add(QueryField.gainEq("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserAccountDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, UserAccountDto.class);
            //取得 排序配置
            AntdvSortMap sortMap = parseSortJsonToBean(sortObj, true);
            result = userAccountService.dealQueryPageByDtos(loginUserInfo, result, queryFieldList, paginationBean, sortMap);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String accountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
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
            result.putBean(userAccountVo);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedRole")
    @PostMapping(value = "/gainGrantedRole")
    public WebResult gainGrantedRole(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            List<DefineRoleEntity> defineRoleEntityList = defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.putResultList(DefineRoleTransfer.transferEntityToVoList(defineRoleEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedPermission")
    @PostMapping(value = "/gainGrantedPermission")
    public WebResult gainGrantedPermission(HttpServletRequest request, String userAccountId,
                                           @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            List<DefinePermissionEntity> definePermissionEntityList = definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
            result.putResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissionEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询已获授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedJob")
    @PostMapping(value = "/gainGrantedJob")
    public WebResult gainGrantedJob(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            List<DefineJobEntity> defineJobEntityList = defineJobMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
            result.putResultList(DefineJobTransfer.transferEntityToVoList(defineJobEntityList));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj",UserAccountVo.class,true);
            Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userAccountService.dealCreate(loginUserInfo, userAccountVo);
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        try {
            UserAccountVo userAccountVo = this.getBeanFromRequest(request, "formObj",UserAccountVo.class , true);
            Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = userAccountService.dealUpdate(loginUserInfo, userAccountVo);
            result.putCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/userAccount/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            //批量伪删除
            delCount = userAccountService.dealBatchLogicDelete(loginUserInfo, delIds);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/userAccount/deleteById")
    @ApiOperation(value = "伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = userAccountService.dealLogicDeleteById(loginUserInfo, delId);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新/批量修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/batchUpdateLockByIds")
    @PostMapping(value = "/batchUpdateLockByIds")
    public WebResult batchUpdateLockByIds(HttpServletRequest request, String[] lockIds, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        try {
            Assert.notEmpty(lockIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            //批量伪删除
            lockCount = userAccountService.dealBatchRenewLock(loginUserInfo, lockIds, lockFlag);
            result.putCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新/修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateLockById")
    @PostMapping(value = "/updateLockById")
    public WebResult updateLockById(HttpServletRequest request, String lockId, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        try {
            Assert.notNull(lockId, BaseRstMsgConstant.ErrorMsg.unknowId());
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true;
            String lockMsg = lockFlag ? "锁定" : "解锁";
            lockCount = userAccountService.dealRenewLock(loginUserInfo, lockId, lockFlag);
            result.putCount(lockCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantRoleToUser")
    @PostMapping(value = "/grantRoleToUser")
    public WebResult doGrantRoleToUser(HttpServletRequest request, String userAccountId, String[] checkIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notNull(userAccountId, "未知用户id:" + actionFailMsg);
            Integer grantCount = userAccountService.dealGrantRoleToUser(loginUserInfo, userAccountId, checkIds);
            result.putCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantJobToUser")
    @PostMapping(value = "/grantJobToUser")
    public WebResult doGrantJobToUser(HttpServletRequest request, String userAccountId, String[] checkIds,
                                      @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notNull(userAccountId, "未知用户id:" + actionFailMsg);
            Integer grantCount = userAccountService.dealGrantJobToUser(loginUserInfo, userAccountId, checkIds);
            result.putCount(grantCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/reflushSecurePwd")
    @GetMapping(value = "/reflushSecurePwd")
    public WebResult reflushSecurePwd(HttpServletRequest request,@CurrentLoginUser(required = false) CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            boolean flag = userAccountService.reflushSecurePwd(loginUserInfo);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
