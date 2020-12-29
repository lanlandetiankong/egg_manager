package com.egg.manager.em.web.controller.emctl.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.facade.api.exchange.BaseController;
import com.egg.manager.facade.api.services.em.user.basic.EmUserAppRelatedService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.facade.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.facade.persistence.em.user.pojo.transfer.EmUserAppRelatedTransfer;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAppRelatedVo;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.facade.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.facade.persistence.enhance.annotation.user.CurrentLoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description app用户关联表-Api
 * @date 2020-12-07
 */
@Slf4j
@Api(value = "API-app用户关联表")
@RestController
@RequestMapping("/emCtl/emUserAppRelated")
public class EmUserAppRelatedController extends BaseController {

    @Reference
    private EmUserAppRelatedService emUserAppRelatedService;


    @ApiOperation(value = "分页查询(dto)->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/emUserAppRelated/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmUserAppRelatedDto.class) QueryPageBean<EmUserAppRelatedDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emUserAppRelatedService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/emUserAppRelated/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String emUserAppRelatedId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(emUserAppRelatedId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmUserAppRelatedEntity emUserAppRelatedEntity = emUserAppRelatedService.getById(emUserAppRelatedId);
        result.putBean(EmUserAppRelatedTransfer.transferEntityToVo(emUserAppRelatedEntity));
        return result;
    }

    @ApiOperation(value = "新增->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/emUserAppRelated/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmUserAppRelatedVo emUserAppRelatedVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emUserAppRelatedVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emUserAppRelatedService.dealCreate(loginUserInfo, emUserAppRelatedVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/emUserAppRelated/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmUserAppRelatedVo emUserAppRelatedVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emUserAppRelatedVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emUserAppRelatedService.dealUpdate(loginUserInfo, emUserAppRelatedVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/emUserAppRelated/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                      @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emUserAppRelatedService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/emUserAppRelated/deleteById")
    @ApiOperation(value = "逻辑删除->app用户关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emUserAppRelatedService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}