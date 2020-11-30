package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.util.page.PageUtil;
import com.egg.manager.persistence.em.define.db.mysql.entity.*;
import com.egg.manager.persistence.em.define.db.mysql.mapper.*;
import com.egg.manager.persistence.em.define.pojo.transfer.*;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.transfer.UserAccountTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.api.exchange.BaseController;
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
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = UserAccountDto.class) QueryPageBean<UserAccountDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = userAccountService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String accountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
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
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedRole")
    @PostMapping(value = "/gainGrantedRole")
    public WebResult gainGrantedRole(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<DefineRoleEntity> defineRoleEntityList = defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        result.putGridList(DefineRoleTransfer.transferEntityToVoList(defineRoleEntityList));
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedPermission")
    @PostMapping(value = "/gainGrantedPermission")
    public WebResult gainGrantedPermission(HttpServletRequest request, String userAccountId,
                                           @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<DefinePermissionEntity> definePermissionEntityList = definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        result.putGridList(DefinePermissionTransfer.transferEntityToVoList(definePermissionEntityList));
        return result;
    }

    @ApiOperation(value = "查询已获授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/userAccount/gainGrantedJob")
    @PostMapping(value = "/gainGrantedJob")
    public WebResult gainGrantedJob(HttpServletRequest request, String userAccountId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<DefineJobEntity> defineJobEntityList = defineJobMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        result.putGridList(DefineJobTransfer.transferEntityToVoList(defineJobEntityList));
        return result;
    }

    @ApiOperation(value = "新增->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        UserAccountVo userAccountVo = PageUtil.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
        Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = userAccountService.dealCreate(loginUserInfo, userAccountVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        UserAccountVo userAccountVo = PageUtil.getBeanFromRequest(request, "formObj", UserAccountVo.class, true);
        Assert.notNull(userAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = userAccountService.dealUpdate(loginUserInfo, userAccountVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/user/userAccount/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        //批量伪删除
        delCount = userAccountService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/user/userAccount/deleteById")
    @ApiOperation(value = "伪删除->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        delCount = userAccountService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @ApiOperation(value = "更新/批量修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/batchUpdateLockByIds")
    @PostMapping(value = "/batchUpdateLockByIds")
    public WebResult batchUpdateLockByIds(HttpServletRequest request, String[] lockIds, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        Assert.notEmpty(lockIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
//操作类型为锁定？如果没传递值默认锁定
        lockFlag = lockFlag != null ? lockFlag : true;
        String lockMsg = lockFlag ? "锁定" : "解锁";
        //批量伪删除
        lockCount = userAccountService.dealBatchRenewLock(loginUserInfo, lockIds, lockFlag);
        result.putCount(lockCount);
        return result;
    }

    @ApiOperation(value = "更新/修改状态->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/updateLockById")
    @PostMapping(value = "/updateLockById")
    public WebResult updateLockById(HttpServletRequest request, String lockId, Boolean lockFlag, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer lockCount = 0;
        Assert.notNull(lockId, BaseRstMsgConstant.ErrorMsg.unknowId());
        //操作类型为锁定？如果没传递值默认锁定
        lockFlag = lockFlag != null ? lockFlag : true;
        String lockMsg = lockFlag ? "锁定" : "解锁";
        lockCount = userAccountService.dealRenewLock(loginUserInfo, lockId, lockFlag);
        result.putCount(lockCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantRoleToUser")
    @PostMapping(value = "/grantRoleToUser")
    public WebResult doGrantRoleToUser(HttpServletRequest request, String userAccountId, String[] checkIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(userAccountId,BaseRstMsgConstant.ErrorMsg.unknowUserId());
        Integer grantCount = userAccountService.dealGrantRoleToUser(loginUserInfo, userAccountId, checkIds);
        result.putCount(grantCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/grantJobToUser")
    @PostMapping(value = "/grantJobToUser")
    public WebResult doGrantJobToUser(HttpServletRequest request, String userAccountId, String[] checkIds,
                                      @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(userAccountId, BaseRstMsgConstant.ErrorMsg.unknowUserId());
        Integer grantCount = userAccountService.dealGrantJobToUser(loginUserInfo, userAccountId, checkIds);
        result.putCount(grantCount);
        return result;
    }

    @ApiOperation(value = "授权/用户账号->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/userAccount/reflushSecurePwd")
    @GetMapping(value = "/reflushSecurePwd")
    public WebResult reflushSecurePwd(HttpServletRequest request, @CurrentLoginUser(required = false) CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        boolean flag = userAccountService.reflushSecurePwd(loginUserInfo);
        return result;
    }
}
