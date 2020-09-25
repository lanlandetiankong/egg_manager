package com.egg.manager.web.controller.organization;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.module.DefineModuleFuncModuleConstant;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
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
import org.apache.commons.lang3.StringUtils;
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
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  DefineTenantController ", description = "租户定义接口")
@RestController
@RequestMapping("/organization/define_tenant")
public class DefineTenantController extends BaseController {

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Reference
    private DefineTenantService defineTenantService;


    @PcWebQueryLog(action = "查询租户定义信息-Dto列表", description = "查询租户定义信息-Dto列表", fullPath = "/organization/define_tenant/getAllDefineTenantDtos")
    @ApiOperation(value = "查询租户定义信息-Dto列表", notes = "查询租户定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineTenantDtos")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineTenantDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineTenantDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询租户定义信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询租户定义信息", notes = "根据租户定义id查询租户定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询租户定义信息", description = "根据租户定义id查询租户定义信息", fullPath = "/organization/define_tenant/getDefineTenantById")
    @PostMapping(value = "/getDefineTenantById")
    public MyCommonResult<DefineTenantVo> doGetDefineTenantById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>();
        try {
            Assert.notBlank(defineTenantId,BaseRstMsgConstant.ErrorMsg.unknowId());
            DefineTenant defineTenant = defineTenantMapper.selectById(defineTenantId);
            result.setBean(DefineTenantTransfer.transferEntityToVo(defineTenant));
            dealCommonSuccessCatch(result, "查询租户定义信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebQueryLog(action = "查询租户定义信息-Enum列表", description = "查询租户定义信息-Enum列表", fullPath = "/organization/define_tenant/getAllDefineTenantEnums")
    @ApiOperation(value = "查询租户定义信息-Enum列表", notes = "查询租户定义信息-Enum列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineTenantEnums")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantEnums(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>();
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineTenantService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, null, sortBeans);
            result = defineTenantService.dealResultListToEnums(loginUser, result);
            dealCommonSuccessCatch(result, "查询租户定义信息Select列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增租户定义", notes = "表单方式新增租户定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增租户定义", description = "表单方式新增租户定义", fullPath = "/organization/define_tenant/doAddDefineTenant")
    @PostMapping(value = "/doAddDefineTenant")
    public MyCommonResult<DefineTenantVo> doAddDefineTenant(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>();
        Integer addCount = 0;
        try {
            Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineTenantService.dealCreate(loginUser, defineTenantVo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, DefineModuleFuncModuleConstant.Success.create);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新租户定义", notes = "表单方式更新租户定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新租户定义", description = "表单方式更新租户定义", fullPath = "/organization/define_tenant/doUpdateDefineTenant")
    @PostMapping(value = "/doUpdateDefineTenant")
    public MyCommonResult doUpdateDefineTenant(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineTenantVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineTenantService.dealUpdate(loginUser, defineTenantVo, false);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result, DefineModuleFuncModuleConstant.Success.update);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除租户定义", description = "根据租户定义id批量删除租户定义", fullPath = "/organization/define_tenant/batchDelDefineTenantByIds")
    @ApiOperation(value = "批量删除租户定义", notes = "根据租户定义id批量删除租户定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的租户定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefineTenantByIds")
    public MyCommonResult doBatchDeleteDefineTenantByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineTenantService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result, DefineModuleFuncModuleConstant.Success.batchDeleteByIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除租户定义", description = "根据租户id删除租户定义", fullPath = "/organization/define_tenant/delOneDefineTenantById")
    @ApiOperation(value = "删除租户定义", notes = "根据租户id删除租户定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的租户定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefineTenantById")
    public MyCommonResult doDelOneDefineTenantById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineTenantService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, DefineModuleFuncModuleConstant.Success.deleteById);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "租户-设置管理员",fullPath = "/organization/define_tenant/setupTenantManager")
    @ApiOperation(value = "租户-设置管理员", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantId", value = "要配置的租户定义id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userAccountIdArr", value = "要设置为管理员的用户id-数组", required = true, dataTypeClass = String.class,allowMultiple=true),
    })
    @PostMapping(value = "/setupTenantManager")
    public MyCommonResult doSetupTenantManager(HttpServletRequest request, String tenantId,String[] userAccountIdArr,
                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(tenantId,"未知租户id:"+actionFailMsg);
            DefineTenant defineTenant = defineTenantMapper.selectById(tenantId);
            Assert.notNull(defineTenant,"租户不存在:"+actionFailMsg);
            int count = defineTenantService.dealTenantSetupManager(loginUser,tenantId,userAccountIdArr);
            result.setCount(count);
            dealCommonSuccessCatch(result, "租户-设置管理员:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}
