package com.egg.manager.em.web.controller.emctl.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.facade.api.exchange.BaseController;
import com.egg.manager.facade.api.services.em.define.basic.EmDefineJobService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineJobMapper;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineJobDto;
import com.egg.manager.facade.persistence.em.define.pojo.transfer.EmDefineJobTransfer;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineJobVo;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.facade.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.facade.persistence.enhance.annotation.user.CurrentLoginUser;
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
@Api(value = "API-职务定义接口 ")
@RestController
@RequestMapping("/emCtl/define/defineJob")
public class EmDefineJobController extends BaseController {
    @Autowired
    private EmDefineJobMapper emDefineJobMapper;
    @Reference
    private EmDefineJobService emDefineJobService;

    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineJob/queryPage")
    @ApiOperation(value = "分页查询->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, @QueryPage(tClass = EmDefineJobEntity.class) QueryPageBean<EmDefineJobEntity> queryPageBean,
                               @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = emDefineJobService.dealQueryPageByEntitys(loginUserInfo, result, queryPageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineJob/queryDtoPage")
    @ApiOperation(value = "分页查询(Dto)->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmDefineJobDto.class) QueryPageBean<EmDefineJobDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emDefineJobService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineJob/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineJobId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        EmDefineJobEntity emDefineJobEntity = emDefineJobMapper.selectById(defineJobId);
        result.putBean(EmDefineJobTransfer.transferEntityToVo(emDefineJobEntity));
        return result;
    }

    @ApiOperation(value = "新增->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineJob/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefineJobVo emDefineJobVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emDefineJobVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefineJobService.dealCreate(loginUserInfo, emDefineJobVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineJob/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefineJobVo emDefineJobVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emDefineJobVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefineJobService.dealUpdate(loginUserInfo, emDefineJobVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineJob/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        //批量逻辑删除
        delCount = emDefineJobService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineJob/deleteById")
    @ApiOperation(value = "逻辑删除->职务定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        delCount = emDefineJobService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
