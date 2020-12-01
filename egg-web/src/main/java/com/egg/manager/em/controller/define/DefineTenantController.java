package com.egg.manager.em.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.DefineTenantService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineTenantTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineTenantVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
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
public class DefineTenantController extends BaseController {
    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Reference
    private DefineTenantService defineTenantService;

    @PcWebQueryLog(fullPath = "/organization/defineTenant/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = DefineTenantDto.class) QueryPageBean<DefineTenantDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = defineTenantService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/organization/defineTenant/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineTenantId, BaseRstMsgConstant.ErrorMsg.unknowId());
        DefineTenantEntity defineTenantEntity = defineTenantMapper.selectById(defineTenantId);
        result.putBean(DefineTenantTransfer.transferEntityToVo(defineTenantEntity));
        return result;
    }

    @PcWebQueryLog(fullPath = "/organization/defineTenant/gainEnumSelect")
    @ApiOperation(value = "查询枚举下拉->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public WebResult doGetAllDefineTenantEnums(HttpServletRequest request, @QueryPage(tClass = DefineTenantDto.class) QueryPageBean<DefineTenantDto> queryPageBean,
                                               @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = defineTenantService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        result = defineTenantService.dealResultListToEnums(loginUserInfo, result);
        return result;
    }

    @ApiOperation(value = "新增->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/organization/defineTenant/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = defineTenantService.dealCreate(loginUserInfo, defineTenantVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/organization/defineTenant/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineTenantVo defineTenantVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(defineTenantVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = defineTenantService.dealUpdate(loginUserInfo, defineTenantVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/organization/defineTenant/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser(required = false) UserAccountEntity loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = defineTenantService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/organization/defineTenant/deleteById")
    @ApiOperation(value = "伪删除->租户定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = defineTenantService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/organization/defineTenant/setupTenantManager")
    @ApiOperation(value = "更新->租户定义/设置管理员", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantId", value = "要配置的租户定义id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userAccountIdArr", value = "要设置为管理员的用户id-数组", required = true, dataTypeClass = String.class, allowMultiple = true),
    })
    @PostMapping(value = "/setupTenantManager")
    public WebResult doSetupTenantManager(HttpServletRequest request, String tenantId, String[] userAccountIdArr,
                                          @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(tenantId,  BaseRstMsgConstant.ErrorMsg.unknowTenantId());
        DefineTenantEntity defineTenantEntity = defineTenantMapper.selectById(tenantId);
        Assert.notNull(defineTenantEntity, BaseRstMsgConstant.ErrorMsg.invalidObject());
        int count = defineTenantService.dealTenantSetupManager(loginUserInfo, tenantId, userAccountIdArr);
        result.putCount(count);
        return result;
    }
}
