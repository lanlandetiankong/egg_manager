package com.egg.manager.web.controller.organization;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.organization.DefineTenantFuncModuleConstant;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.transfer.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \
 */
@Slf4j
@Api(value = "API-租户定义接口")
@RestController
@RequestMapping("/organization/define_tenant")
public class DefineTenantController extends BaseController {

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Reference
    private DefineTenantService defineTenantService;


    @PcWebQueryLog(action = "分页查询(dto)->租户定义",fullPath = "/organization/define_tenant/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->租户定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineTenantVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class, DefineTenantFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineTenantDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineTenantDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(action = "根据id查询->租户定义",fullPath = "/organization/define_tenant/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineTenantVo> queryOneById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class, DefineTenantFuncModuleConstant.Success.QUERY_PAGE);
        try {
            Assert.notBlank(defineTenantId,BaseRstMsgConstant.ErrorMsg.unknowId());
            DefineTenant defineTenant = defineTenantMapper.selectById(defineTenantId);
            result.setBean(DefineTenantTransfer.transferEntityToVo(defineTenant));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @PcWebQueryLog(action = "查询枚举下拉->租户定义",fullPath = "/organization/define_tenant/gainEnumSelect")
    @ApiOperation(value = "查询枚举下拉->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantEnums(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = MyCommonResult.gainQueryResult(DefineTenantVo.class, DefineTenantFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>();
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, null, sortBeans);
            result = defineTenantService.dealResultListToEnums(loginUser, result);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "新增->租户定义",fullPath = "/organization/define_tenant/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineTenantFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineTenantService.dealCreate(loginUser, defineTenantVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(action = "更新->租户定义",fullPath = "/organization/define_tenant/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineTenantFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(defineTenantVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineTenantService.dealUpdate(loginUser, defineTenantVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量伪删除->租户定义",fullPath = "/organization/define_tenant/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineTenantFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineTenantService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


    @PcWebOperationLog(action = "伪删除->租户定义",fullPath = "/organization/define_tenant/deleteById")
    @ApiOperation(value = "伪删除->租户定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的id数组", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineTenantFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineTenantService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }


    @PcWebOperationLog(action = "更新->租户定义/设置管理员",fullPath = "/organization/define_tenant/setupTenantManager")
    @ApiOperation(value = "更新->租户定义/设置管理员", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantId", value = "要配置的租户定义id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userAccountIdArr", value = "要设置为管理员的用户id-数组", required = true, dataTypeClass = String.class,allowMultiple=true),
    })
    @PostMapping(value = "/setupTenantManager")
    public MyCommonResult doSetupTenantManager(HttpServletRequest request, String tenantId,String[] userAccountIdArr,
                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineTenantFuncModuleConstant.Success.SETTING_OPER);
        try {
            Assert.notBlank(tenantId,"未知租户id:"+actionFailMsg);
            DefineTenant defineTenant = defineTenantMapper.selectById(tenantId);
            Assert.notNull(defineTenant,"租户不存在:"+actionFailMsg);
            int count = defineTenantService.dealTenantSetupManager(loginUser,tenantId,userAccountIdArr);
            result.setCount(count);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineTenantFuncModuleConstant.Failure.SETTING_OPER);
        }
        return result;
    }

}
