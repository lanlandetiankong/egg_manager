package com.egg.manager.em.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.DefineModuleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineModuleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineModuleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineModuleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineModuleVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
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
@Api(value = "API-模块定义接口")
@RestController
@RequestMapping("/module/define_module")
public class DefineModuleController extends BaseController {
    @Autowired
    private DefineModuleMapper defineModuleMapper;
    @Reference
    private DefineModuleService defineModuleService;

    @EmPcWebQueryLog(fullPath = "/module/define_module/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = DefineModuleDto.class) QueryPageBean<DefineModuleDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = defineModuleService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/module/define_module/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineModuleId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        DefineModuleEntity defineModuleEntity = defineModuleMapper.selectById(defineModuleId);
        result.putBean(DefineModuleTransfer.transferEntityToVo(defineModuleEntity));
        return result;
    }

    @ApiOperation(value = "新增->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/module/define_module/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(defineModuleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = defineModuleService.dealCreate(loginUserInfo, defineModuleVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/module/define_module/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(defineModuleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = defineModuleService.dealUpdate(loginUserInfo, defineModuleVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/module/define_module/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = defineModuleService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/module/define_module/deleteById")
    @ApiOperation(value = "伪删除->模块定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = defineModuleService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
