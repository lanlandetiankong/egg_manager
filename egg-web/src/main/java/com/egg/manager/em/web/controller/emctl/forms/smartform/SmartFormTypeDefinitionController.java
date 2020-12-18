package com.egg.manager.em.web.controller.emctl.forms.smartform;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormDefinitionMgoService;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormTypeDefinitionMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.db.MongoFieldConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.verification.smartform.SmartFormTypeDefinitionMongoVerifyO;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfUpdate;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-智能表单类型定义")
@RestController
@RequestMapping("/emCtl/forms/smartForm/formTypeDefinition")
public class SmartFormTypeDefinitionController extends BaseController {
    @Reference
    private SmartFormTypeDefinitionMgoService smartFormTypeDefinitionMgoService;
    @Reference
    private SmartFormDefinitionMgoService smartFormDefinitionMgoService;

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @QueryPage(tClass = SmartFormTypeDefinitionMgo.class) QueryPageBean<SmartFormTypeDefinitionMgo> queryPageBean,
                                   @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        AntdvPage<SmartFormTypeDefinitionMgo> pageBean = smartFormTypeDefinitionMgoService.doFindPage(loginUserInfo, queryPageBean);
        result.putPage(pageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public WebResult doGetDataAll(HttpServletRequest request, @QueryPage(tClass = SmartFormTypeDefinitionMgo.class) QueryPageBean<SmartFormTypeDefinitionMgo> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        List<SmartFormTypeDefinitionMgo> list = smartFormTypeDefinitionMgoService.doFindAll(loginUserInfo, queryPageBean);
        result = smartFormTypeDefinitionMgoService.dealResultListToEnums(result, list);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public WebResult doGetOneItemById(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                      @RequestParam(value = FieldConst.FIELD_FID, required = true) String fid) {
        WebResult result = WebResult.okQuery();
        Assert.notNull(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
        SmartFormTypeDefinitionMgo mobj = smartFormTypeDefinitionMgoService.doFindById(loginUserInfo, fid);
        result.putBean(mobj);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/addByForm")
    @ApiOperation(value = "新增->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public WebResult doAddByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                 SmartFormTypeDefinitionMgo formTypeDefinitionMgo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(formTypeDefinitionMgo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        SmartFormTypeDefinitionMgo newMgo = smartFormTypeDefinitionMgoService.doInsert(loginUserInfo, formTypeDefinitionMgo);
        addCount += (newMgo != null) ? 1 : 0;
        result.putCount(addCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/updateByForm")
    @ApiOperation(value = "更新->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public WebResult doUpdateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                    SmartFormTypeDefinitionMgo formTypeDefinitionMgo) {
        WebResult result = WebResult.okOperation();
        Integer count = 0;
        Assert.notNull(formTypeDefinitionMgo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        SmartFormTypeDefinitionMgo newMgo = smartFormTypeDefinitionMgoService.doUpdateById(loginUserInfo, formTypeDefinitionMgo);
        //更新了一条数据
        if (newMgo != null) {
            count += 1;
            //触发formDefinetion 更新
            smartFormDefinitionMgoService.updateFormTypeByTypeId(loginUserInfo, newMgo);
        }
        result.putCount(count);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/delOneById")
    @ApiOperation(value = "逻辑删除->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public WebResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notNull(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Long delCount = smartFormTypeDefinitionMgoService.doLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formTypeDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单类型定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public WebResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Long delCount = (long) 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = smartFormTypeDefinitionMgoService.doLogicDeleteByIds(loginUserInfo, Lists.newArrayList(delIds));
        result.putCount(delCount);
        return result;
    }
}
