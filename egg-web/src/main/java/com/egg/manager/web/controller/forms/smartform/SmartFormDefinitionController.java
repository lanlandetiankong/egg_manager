package com.egg.manager.web.controller.forms.smartform;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.forms.mongo.smartform.SmartFormDefinitionMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBean;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBuffer;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryPageBean;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.db.mongo.repository.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormDefinitionMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormDefinitionMgvo;
import com.egg.manager.persistence.em.forms.pojo.verification.smartform.SmartFormDefinitionMongoVerifyO;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Optional;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-智能表单定义")
@RestController
@RequestMapping("/forms/smartForm/formDefinition")
public class SmartFormDefinitionController extends BaseController {

    @Autowired
    private SmartFormTypeDefinitionRepository smartFormTypeDefinitionRepository;

    @Reference
    private SmartFormDefinitionMgoService smartFormDefinitionMgoService;


    @PcWebQueryLog(fullPath = "/forms/smartForm/formDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //添加状态过滤,时间倒序排序
            MongoQueryBuffer mongoQueryBuffer = new MongoQueryBuffer(MyMongoCommonQueryFieldEnum.IsDeleted_Eq_Not)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MongoQueryPageBean<SmartFormDefinitionMgo> pageBean = smartFormDefinitionMgoService.doFindPage(loginUserInfo, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(fullPath = "/forms/smartForm/formDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public WebResult doGetDataAll(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //添加状态过滤,时间倒序排序
            MongoQueryBuffer mongoQueryBuffer = new MongoQueryBuffer(MyMongoCommonQueryFieldEnum.IsDeleted_Eq_Not)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MongoQueryPageBean<SmartFormDefinitionMgo> pageBean = smartFormDefinitionMgoService.doFindPage(loginUserInfo, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(fullPath = "/forms/smartForm/formDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public WebResult doGetOneItemById(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo,
                                      @RequestParam(value = "fid", required = true) String fid) {
        WebResult result = WebResult.okQuery();
        try {
            Assert.notNull(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
            SmartFormDefinitionMgo mobj = smartFormDefinitionMgoService.doFindById(loginUserInfo, fid);
            result.putBean(mobj);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/forms/smartForm/formDefinition/addByForm")
    @ApiOperation(value = "新增->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public WebResult doAddByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo,
                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                 SmartFormDefinitionMgvo smartFormDefinitionMgvo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            Assert.notNull(smartFormDefinitionMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            Optional<SmartFormTypeDefinitionMgo> formTypeDefinitionMgoOptional = smartFormTypeDefinitionRepository.findById(smartFormDefinitionMgvo.getFormTypeId());
            if (formTypeDefinitionMgoOptional.isPresent() == false) {
                throw new BusinessException("不是有效的表单类型！");
            }
            SmartFormDefinitionMgo formDefinitionMgo = SmartFormDefinitionMapstruct.INSTANCE.translateMgvoToMgo(smartFormDefinitionMgvo);
            formDefinitionMgo.setFormType(formTypeDefinitionMgoOptional.get());
            SmartFormDefinitionMgo newMgo = smartFormDefinitionMgoService.doInsert(loginUserInfo, formDefinitionMgo);
            addCount += (newMgo != null) ? 1 : 0;
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/forms/smartForm/formDefinition/updateByForm")
    @ApiOperation(value = "更新->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public WebResult doUpdateByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo,
                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                    SmartFormDefinitionMgvo formDefinitionMgvo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            Assert.notNull(formDefinitionMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            Optional<SmartFormTypeDefinitionMgo> formTypeDefinitionMgoOptional = smartFormTypeDefinitionRepository.findById(formDefinitionMgvo.getFormTypeId());
            if (formTypeDefinitionMgoOptional.isPresent() == false) {
                throw new BusinessException("不是有效的表单类型！");
            }
            SmartFormDefinitionMgo formDefinitionMgo = SmartFormDefinitionMapstruct.INSTANCE.translateMgvoToMgo(formDefinitionMgvo);
            formDefinitionMgo.setFormType(formTypeDefinitionMgoOptional.get());
            SmartFormDefinitionMgo newMgo = smartFormDefinitionMgoService.doUpdateById(loginUserInfo, formDefinitionMgo);
            addCount += (newMgo != null) ? 1 : 0;
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/forms/smartForm/formDefinition/delOneById")
    @ApiOperation(value = "伪删除->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public WebResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notNull(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = smartFormDefinitionMgoService.doFakeDeleteById(loginUserInfo, delId);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/forms/smartForm/formDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public WebResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = smartFormDefinitionMgoService.doFakeDeleteByIds(loginUserInfo, Lists.newArrayList(delIds));
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
