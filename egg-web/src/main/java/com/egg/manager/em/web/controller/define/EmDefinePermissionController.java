package com.egg.manager.em.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefinePermissionService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.PublicResultEnum;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefinePermissionTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefinePermissionVo;
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
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-权限定义接口")
@RestController
@RequestMapping("/define/definePermission")
public class EmDefinePermissionController extends BaseController {
    @Autowired
    private EmDefinePermissionMapper emDefinePermissionMapper;
    @Reference
    private EmDefinePermissionService emDefinePermissionService;

    @EmPcWebQueryLog(fullPath = "/define/definePermission/queryPage")
    @ApiOperation(value = "分页查询->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, @QueryPage(tClass = EmDefinePermissionEntity.class) QueryPageBean<EmDefinePermissionEntity> queryPageBean,
                               @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEq("ensure", SwitchStateEnum.Open.getValue());
        result = emDefinePermissionService.dealQueryPageByEntitys(loginUserInfo, result, queryPageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/define/definePermission/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmDefinePermissionDto.class) QueryPageBean<EmDefinePermissionDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = emDefinePermissionService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/define/definePermission/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String definePermissionId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        EmDefinePermissionEntity emDefinePermissionEntity = emDefinePermissionMapper.selectById(definePermissionId);
        result.putBean(EmDefinePermissionTransfer.transferEntityToVo(emDefinePermissionEntity));
        return result;
    }

    @ApiOperation(value = "新增->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/define/definePermission/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefinePermissionVo emDefinePermissionVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emDefinePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefinePermissionService.dealCreate(loginUserInfo, emDefinePermissionVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/define/definePermission/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefinePermissionVo emDefinePermissionVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emDefinePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefinePermissionService.dealUpdate(loginUserInfo, emDefinePermissionVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/define/definePermission/batchEnsureByIds")
    @ApiOperation(value = "更新/批量启用->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要启用的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchEnsureByIds")
    public WebResult batchEnsureByIds(HttpServletRequest request, String[] ensureIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(ensureIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefinePermissionService.dealBatchEnsure(loginUserInfo, ensureIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/define/definePermission/deleteById")
    @ApiOperation(value = "逻辑删除->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emDefinePermissionService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        if (new Integer(0).equals(delCount)) {
            //如果删除的是 [已启用的]，则抛出异常
            throw new MyRuntimeBusinessException("删除权限定义:" + BaseRstMsgConstant.ACTION_FAIL_MSG + PublicResultEnum.SwitchOpenChangeLimit.getLabel());
        }
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/define/definePermission/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefinePermissionService.dealBatchLogicDelete(loginUserInfo, delIds);
        if (delIds.length > delCount) {
            result.putErrorMsg("由于部分权限已经确认启用后无法删除！预计删除" + delIds.length + "条数据，实际删除" + delCount + "条数据。");
        }
        result.putCount(delCount);
        return result;
    }
}
