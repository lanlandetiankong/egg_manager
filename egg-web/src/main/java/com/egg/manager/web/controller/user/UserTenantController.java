package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.user.UserRoleFuncModuleConstant;
import com.egg.manager.api.constants.funcModule.user.UserTenantFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserTenantService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  UserTenantController ", description = "用户与租户关联表的接口")
@RestController
@RequestMapping("/user/user_tenant")
public class UserTenantController extends BaseController {


    @Autowired
    private UserTenantMapper userTenantMapper;
    @Reference
    private UserTenantService userTenantService;


    @PcWebQueryLog(action = "查询 [用户与租户关联] 列表", description = "查询 [用户与租户关联] 列表", fullPath = "/user/user_tenant/getAllUserTenants")
    @ApiOperation(value = "查询 [用户与租户关联] 列表", notes = "查询 [用户与租户关联] 列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllUserTenants")
    public MyCommonResult<UserTenantVo> doGetAllUserTenants(HttpServletRequest request,
                                                            String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserTenantVo> result = new MyCommonResult<UserTenantVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserTenant> paginationBean = this.parsePaginationJsonToBean(paginationObj,UserTenant.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userTenantService.dealQueryPageByEntitys(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询 [用户与租户关联] 信息列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询 [用户与租户关联] 信息", notes = "根据 [用户与租户关联] id查询 [用户与租户关联] 信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询 [用户与租户关联] 信息", description = "根据 [用户与租户关联] id查询 [用户与租户关联] 信息", fullPath = "/user/user_tenant/getUserTenantById")
    @PostMapping(value = "/getUserTenantById")
    public MyCommonResult<UserTenantVo> doGetUserTenantById(HttpServletRequest request, String tenantId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserTenantVo> result = new MyCommonResult<UserTenantVo>();
        try {
            Assert.notBlank(tenantId, BaseRstMsgConstant.ErrorMsg.unknowId());
            UserTenant vo = userTenantMapper.selectById(tenantId);
            result.setBean(UserTenantTransfer.transferEntityToVo(vo));
            dealCommonSuccessCatch(result, "查询 [用户与租户关联] 信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增 [用户与租户关联] ", notes = "表单方式新增 [用户与租户关联] ", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增 [用户与租户关联] ", description = "表单方式新增 [用户与租户关联] ", fullPath = "/user/user_tenant/doAddUserTenant")
    @PostMapping(value = "/doAddUserTenant")
    public MyCommonResult doAddUserTenant(HttpServletRequest request, UserTenantVo userTenantVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer addCount = 0;
        try {
            Assert.notNull(userTenantVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userTenantService.dealCreate(loginUser, userTenantVo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, UserTenantFuncModuleConstant.Success.create);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除 [用户与租户关联] ", description = "根据 [用户与租户关联] id批量删除 [用户与租户关联] ", fullPath = "/user/user_tenant/batchDelUserTenantByIds")
    @ApiOperation(value = "批量删除 [用户与租户关联] ", notes = "根据 [用户与租户关联] id批量删除 [用户与租户关联] ", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的 [用户与租户关联] id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelUserTenantByIds")
    public MyCommonResult doBatchDeleteUserTenantById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = userTenantService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result,  UserTenantFuncModuleConstant.Success.batchDeleteByIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除 [用户与租户关联] ", description = "根据 [用户与租户关联] id删除 [用户与租户关联] ", fullPath = "/user/user_tenant/delOneUserTenantByIds")
    @ApiOperation(value = "删除 [用户与租户关联] ", notes = "根据 [用户与租户关联] id删除 [用户与租户关联] ", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的 [用户与租户关联] id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneUserTenantByIds")
    public MyCommonResult doDelOneUserTenantById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            if (StringUtils.isNotBlank(delId)) {
                delCount = userTenantService.dealDeleteById(loginUser, delId);
                dealCommonSuccessCatch(result, UserTenantFuncModuleConstant.Success.deleteById);
            }
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
