package com.egg.manager.em.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.util.basic.page.PageUtil;
import com.egg.manager.persistence.em.define.db.mysql.entity.*;
import com.egg.manager.persistence.em.define.db.mysql.mapper.*;
import com.egg.manager.persistence.em.define.pojo.transfer.*;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.persistence.em.user.pojo.transfer.EmUserAccountTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserAccountVo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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
public class EmUserAccountController extends BaseController {
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;
    @Autowired
    private EmDefineRoleMapper emDefineRoleMapper;
    @Autowired
    private EmDefinePermissionMapper emDefinePermissionMapper;
    @Autowired
    private EmDefineJobMapper emDefineJobMapper;
    @Autowired
    private EmDefineTenantMapper emDefineTenantMapper;
    @Autowired
    private EmDefineDepartmentMapper emDefineDepartmentMapper;
    @Autowired
    private EmUserAccountService emUserAccountService;

    @RequiresRoles(value = {ShiroRoleConstant.ROOT, ShiroRoleConstant.SUPER_ROOT}, logical = Logical.OR)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmUserAccountDto.class) QueryPageBean<EmUserAccountDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = emUserAccountService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/user/userAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String accountId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        EmUserAccountEntity account = emUserAccountMapper.selectById(accountId);
        EmUserAccountVo emUserAccountVo = EmUserAccountTransfer.transferEntityToVo(account);
        //取得 所属的 租户定义
        EmDefineTenantEntity belongTenant = emDefineTenantMapper.selectOneOfUserBelongTenant(account.getFid(), BaseStateEnum.ENABLED.getValue());
        if (belongTenant != null) {
            emUserAccountVo.setBelongTenantId(belongTenant.getFid());
            emUserAccountVo.setBelongTenant(EmDefineTenantTransfer.transferEntityToVo(belongTenant));
        }
        //取得 所属的 部门定义
        EmDefineDepartmentEntity belongDepartment = emDefineDepartmentMapper.selectOneOfUserBelongDepartment(account.getFid(), BaseStateEnum.ENABLED.getValue());
        if (belongDepartment != null) {
            emUserAccountVo.setBelongDepartmentId(belongDepartment.getFid());
            emUserAccountVo.setBelongDepartment(EmDefineDepartmentTransfer.transferEntityToVo(belongDepartment));
        }
        result.putBean(emUserAccountVo);
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/user/userAccount/gainGrantedRole")
    @PostMapping(value = "/gainGrantedRole")
    public WebResult gainGrantedRole(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefineRoleEntity> emDefineRoleEntityList = emDefineRoleMapper.findAllRoleByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        result.putGridList(EmDefineRoleTransfer.transferEntityToVoList(emDefineRoleEntityList));
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/user/userAccount/gainGrantedPermission")
    @PostMapping(value = "/gainGrantedPermission")
    public WebResult gainGrantedPermission(HttpServletRequest request, String userAccountId,
                                           @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefinePermissionEntity> emDefinePermissionEntityList = emDefinePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        result.putGridList(EmDefinePermissionTransfer.transferEntityToVoList(emDefinePermissionEntityList));
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/user/userAccount/gainGrantedJob")
    @PostMapping(value = "/gainGrantedJob")
    public WebResult gainGrantedJob(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefineJobEntity> emDefineJobEntityList = emDefineJobMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        result.putGridList(EmDefineJobTransfer.transferEntityToVoList(emDefineJobEntityList));
        return result;
    }

    @ApiOperation(value = "新增->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        EmUserAccountVo emUserAccountVo = PageUtil.getBeanFromRequest(request, "formObj", EmUserAccountVo.class, true);
        Assert.notNull(emUserAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emUserAccountService.dealCreate(loginUserInfo, emUserAccountVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        EmUserAccountVo emUserAccountVo = PageUtil.getBeanFromRequest(request, "formObj", EmUserAccountVo.class, true);
        Assert.notNull(emUserAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emUserAccountService.dealUpdate(loginUserInfo, emUserAccountVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/user/userAccount/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        //批量伪删除
        delCount = emUserAccountService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/user/userAccount/deleteById")
    @ApiOperation(value = "伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        delCount = emUserAccountService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @ApiOperation(value = "更新/批量修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/batchUpdateLockByIds")
    @PostMapping(value = "/batchUpdateLockByIds")
    public WebResult batchUpdateLockByIds(HttpServletRequest request, String[] lockIds, Boolean lockFlag, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        Assert.notEmpty(lockIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
//操作类型为锁定？如果没传递值默认锁定
        lockFlag = lockFlag != null ? lockFlag : true;
        String lockMsg = lockFlag ? "锁定" : "解锁";
        //批量伪删除
        lockCount = emUserAccountService.dealBatchRenewLock(loginUserInfo, lockIds, lockFlag);
        result.putCount(lockCount);
        return result;
    }

    @ApiOperation(value = "更新/修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/updateLockById")
    @PostMapping(value = "/updateLockById")
    public WebResult updateLockById(HttpServletRequest request, String lockId, Boolean lockFlag, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        Assert.notNull(lockId, BaseRstMsgConstant.ErrorMsg.unknowId());
        //操作类型为锁定？如果没传递值默认锁定
        lockFlag = lockFlag != null ? lockFlag : true;
        String lockMsg = lockFlag ? "锁定" : "解锁";
        lockCount = emUserAccountService.dealRenewLock(loginUserInfo, lockId, lockFlag);
        result.putCount(lockCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/grantRoleToUser")
    @PostMapping(value = "/grantRoleToUser")
    public WebResult doGrantRoleToUser(HttpServletRequest request, String userAccountId, String[] checkIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(userAccountId, BaseRstMsgConstant.ErrorMsg.unknowUserId());
        Integer grantCount = emUserAccountService.dealGrantRoleToUser(loginUserInfo, userAccountId, checkIds);
        result.putCount(grantCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/grantJobToUser")
    @PostMapping(value = "/grantJobToUser")
    public WebResult doGrantJobToUser(HttpServletRequest request, String userAccountId, String[] checkIds,
                                      @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(userAccountId, BaseRstMsgConstant.ErrorMsg.unknowUserId());
        Integer grantCount = emUserAccountService.dealGrantJobToUser(loginUserInfo, userAccountId, checkIds);
        result.putCount(grantCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/user/userAccount/reflushSecurePwd")
    @GetMapping(value = "/reflushSecurePwd")
    public WebResult reflushSecurePwd(HttpServletRequest request, @CurrentLoginUser(required = false) CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        boolean flag = emUserAccountService.reflushSecurePwd(loginUserInfo);
        return result;
    }
}
