package com.egg.manager.em.web.controller.emctl.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefineTenantService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineTenantMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineTenantTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineTenantVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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

/**
 * \* note:
 * @author: \* Date: 2019/9/14
 * \* Time: 23:41
 * \
 */
@Slf4j
@Api(value = "API-租户定义接口")
@RestController
@RequestMapping("/organization/defineTenant")
public class EmDefineTenantController extends BaseController {
    @Autowired
    private EmDefineTenantMapper emDefineTenantMapper;
    @Reference
    private EmDefineTenantService emDefineTenantService;

    @EmPcWebQueryLog(fullPath = "/organization/defineTenant/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmDefineTenantDto.class) QueryPageBean<EmDefineTenantDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emDefineTenantService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/organization/defineTenant/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineTenantId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineTenantEntity emDefineTenantEntity = emDefineTenantMapper.selectById(defineTenantId);
        result.putBean(EmDefineTenantTransfer.transferEntityToVo(emDefineTenantEntity));
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/organization/defineTenant/gainEnumSelect")
    @ApiOperation(value = "查询枚举下拉->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public WebResult doGetAllDefineTenantEnums(HttpServletRequest request, @QueryPage(tClass = EmDefineTenantDto.class) QueryPageBean<EmDefineTenantDto> queryPageBean,
                                               @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = emDefineTenantService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        result = emDefineTenantService.dealResultListToEnums(loginUserInfo, result);
        return result;
    }

    @ApiOperation(value = "新增->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/organization/defineTenant/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefineTenantVo emDefineTenantVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emDefineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefineTenantService.dealCreate(loginUserInfo, emDefineTenantVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/organization/defineTenant/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefineTenantVo emDefineTenantVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emDefineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefineTenantService.dealUpdate(loginUserInfo, emDefineTenantVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/organization/defineTenant/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser(required = false) EmUserAccountEntity loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefineTenantService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/organization/defineTenant/deleteById")
    @ApiOperation(value = "逻辑删除->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emDefineTenantService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/organization/defineTenant/setupTenantManager")
    @ApiOperation(value = "更新->租户定义/设置管理员", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantId", value = "要配置的租户定义id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userAccountIdArr", value = "要设置为管理员的用户id-数组", required = true, dataTypeClass = String.class, allowMultiple = true),
    })
    @PostMapping(value = "/setupTenantManager")
    public WebResult doSetupTenantManager(HttpServletRequest request, String tenantId, String[] userAccountIdArr,
                                          @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(tenantId, BaseRstMsgConstant.ErrorMsg.unknowTenantId());
        EmDefineTenantEntity emDefineTenantEntity = emDefineTenantMapper.selectById(tenantId);
        Assert.notNull(emDefineTenantEntity, BaseRstMsgConstant.ErrorMsg.invalidObject());
        int count = emDefineTenantService.dealTenantSetupManager(loginUserInfo, tenantId, userAccountIdArr);
        result.putCount(count);
        return result;
    }
}
