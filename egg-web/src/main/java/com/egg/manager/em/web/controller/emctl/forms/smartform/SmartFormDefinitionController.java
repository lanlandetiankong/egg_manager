package com.egg.manager.em.web.controller.emctl.forms.smartform;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormDefinitionMgoService;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormTypeDefinitionMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.constant.db.MongoFieldConstant;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormDefinitionMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormDefinitionMgvo;
import com.egg.manager.persistence.em.forms.pojo.verification.smartform.SmartFormDefinitionMongoVerifyO;
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

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-智能表单定义")
@RestController
@RequestMapping("/emCtl/forms/smartForm/formDefinition")
public class SmartFormDefinitionController extends BaseController {
    @Reference
    private SmartFormTypeDefinitionMgoService smartFormTypeDefinitionMgoService;
    @Reference
    private SmartFormDefinitionMgoService smartFormDefinitionMgoService;

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @QueryPage(tClass = SmartFormDefinitionMgo.class) QueryPageBean<SmartFormDefinitionMgo> queryPageBean,
                                   @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        AntdvPage<SmartFormDefinitionMgo> pageBean = smartFormDefinitionMgoService.doFindPage(loginUserInfo, queryPageBean);
        result.putPage(pageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public WebResult doGetDataAll(HttpServletRequest request, @QueryPage(tClass = SmartFormDefinitionMgo.class) QueryPageBean<SmartFormDefinitionMgo> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        AntdvPage<SmartFormDefinitionMgo> pageBean = smartFormDefinitionMgoService.doFindPage(loginUserInfo, queryPageBean);
        result.putPage(pageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/forms/smartForm/formDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public WebResult doGetOneItemById(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                      @RequestParam(value = FieldConst.FIELD_FID, required = true) String fid) {
        WebResult result = WebResult.okQuery();
        Assert.notNull(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
        SmartFormDefinitionMgo mobj = smartFormDefinitionMgoService.doFindById(loginUserInfo, fid);
        result.putBean(mobj);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formDefinition/addByForm")
    @ApiOperation(value = "新增->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public WebResult doAddByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                 SmartFormDefinitionMgvo smartFormDefinitionMgvo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(smartFormDefinitionMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        SmartFormTypeDefinitionMgo smartFormDefinitionMgo = smartFormTypeDefinitionMgoService.doFindById(loginUserInfo, smartFormDefinitionMgvo.getFormTypeId());
        if (smartFormDefinitionMgo == null) {
            throw new MyRuntimeBusinessException(BaseRstMsgConstant.ErrorMsg.formIncorrectParam());
        }
        SmartFormDefinitionMgo formDefinitionMgo = SmartFormDefinitionMapstruct.INSTANCE.translateMgvoToMgo(smartFormDefinitionMgvo);
        formDefinitionMgo.setFormType(smartFormDefinitionMgo);
        SmartFormDefinitionMgo newMgo = smartFormDefinitionMgoService.doInsert(loginUserInfo, formDefinitionMgo);
        addCount += (newMgo != null) ? 1 : 0;
        result.putCount(addCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formDefinition/updateByForm")
    @ApiOperation(value = "更新->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public WebResult doUpdateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                    SmartFormDefinitionMgvo formDefinitionMgvo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(formDefinitionMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        SmartFormTypeDefinitionMgo smartFormTypeDefinitionMgo = smartFormTypeDefinitionMgoService.doFindById(loginUserInfo, formDefinitionMgvo.getFormTypeId());
        if (smartFormTypeDefinitionMgo == null) {
            throw new MyRuntimeBusinessException(BaseRstMsgConstant.ErrorMsg.formIncorrectParam());
        }
        SmartFormDefinitionMgo formDefinitionMgo = SmartFormDefinitionMapstruct.INSTANCE.translateMgvoToMgo(formDefinitionMgvo);
        formDefinitionMgo.setFormType(smartFormTypeDefinitionMgo);
        SmartFormDefinitionMgo newMgo = smartFormDefinitionMgoService.doUpdateById(loginUserInfo, formDefinitionMgo);
        addCount += (newMgo != null) ? 1 : 0;
        result.putCount(addCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formDefinition/delOneById")
    @ApiOperation(value = "逻辑删除->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public WebResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notNull(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Long delCount = smartFormDefinitionMgoService.doLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/forms/smartForm/formDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public WebResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Long delCount = (long) 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = smartFormDefinitionMgoService.doLogicDeleteByIds(loginUserInfo, Lists.newArrayList(delIds));
        result.putCount(delCount);
        return result;
    }
}
