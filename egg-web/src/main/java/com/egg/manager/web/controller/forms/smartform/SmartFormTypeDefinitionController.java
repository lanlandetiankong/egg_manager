package com.egg.manager.web.controller.forms.smartform;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.forms.smartform.SmartFormTypeDefinitionFuncModuleConstant;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormDefinitionMgoService;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormTypeDefinitionMgoService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.query.mongo.MongoQueryBean;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.forms.smartform.SmartFormTypeDefinitionMongoVerifyO;
import com.egg.manager.web.controller.BaseController;
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
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/18
 * \* Time: 14:19
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-智能表单类型定义")
@RestController
@RequestMapping("/forms/smartForm/formTypeDefinition")
public class SmartFormTypeDefinitionController extends BaseController {

    @Reference
    private SmartFormTypeDefinitionMgoService smartFormTypeDefinitionMgoService;
    @Reference
    private SmartFormDefinitionMgoService smartFormDefinitionMgoService;


    @PcWebQueryLog(action = "分页查询->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<SmartFormTypeDefinitionMgo> doGetDataPage(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormTypeDefinitionMgo> result = MyCommonResult.gainQueryResult(SmartFormTypeDefinitionMgo.class, SmartFormTypeDefinitionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<SmartFormTypeDefinitionMgo> pageBean = smartFormTypeDefinitionMgoService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @PcWebQueryLog(action = "分页查询->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public MyCommonResult<SmartFormTypeDefinitionMgo> doGetDataAll(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormTypeDefinitionMgo> result = MyCommonResult.gainQueryResult(SmartFormTypeDefinitionMgo.class,SmartFormTypeDefinitionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            List<SmartFormTypeDefinitionMgo> list = smartFormTypeDefinitionMgoService.doFindAll(loginUser, mongoQueryBuffer);
            result = smartFormTypeDefinitionMgoService.dealResultListToEnums(result, list);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @PcWebQueryLog(action = "根据id查询->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<SmartFormTypeDefinitionMgo> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                       @RequestParam(value = "fid", required = true) String fid) {
        MyCommonResult<SmartFormTypeDefinitionMgo> result = MyCommonResult.gainQueryResult(SmartFormTypeDefinitionMgo.class,SmartFormTypeDefinitionFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            Assert.notBlank(fid,BaseRstMsgConstant.ErrorMsg.unknowId());
            SmartFormTypeDefinitionMgo mobj = smartFormTypeDefinitionMgoService.doFindById(loginUser, fid);
            result.setBean(mobj);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;

    }

    @PcWebOperationLog(action = "新增->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/addByForm")
    @ApiOperation(value = "新增->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public MyCommonResult doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                                                 SmartFormTypeDefinitionMgo formTypeDefinitionMO) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormTypeDefinitionFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        Integer addCount = 0;
        try {
            Assert.notNull(formTypeDefinitionMO, BaseRstMsgConstant.ErrorMsg.emptyForm());
            SmartFormTypeDefinitionMgo newMO = smartFormTypeDefinitionMgoService.doInsert(loginUser, formTypeDefinitionMO);
            addCount += (newMO != null) ? 1 : 0;
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @PcWebOperationLog(action = "更新->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/updateByForm")
    @ApiOperation(value = "更新->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public MyCommonResult doUpdateByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                                                    SmartFormTypeDefinitionMgo formTypeDefinitionMO) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormTypeDefinitionFuncModuleConstant.Success.UPDATE_OPER);
        Integer count = 0;
        try {
            Assert.notNull(formTypeDefinitionMO,BaseRstMsgConstant.ErrorMsg.emptyForm());
            SmartFormTypeDefinitionMgo newMO = smartFormTypeDefinitionMgoService.doUpdateById(loginUser, formTypeDefinitionMO);
            //更新了一条数据
            if (newMO != null) {
                count += 1;
                //触发formDefinetion 更新
                smartFormDefinitionMgoService.updateFormTypeByTypeId(loginUser, newMO);
            }
            result.setCount(count);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(action = "伪删除->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/delOneById")
    @ApiOperation(value = "伪删除->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "指定删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormTypeDefinitionFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = smartFormTypeDefinitionMgoService.doFakeDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除->表单类型定义", fullPath = "/forms/smartForm/formTypeDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单类型定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormTypeDefinitionFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = smartFormTypeDefinitionMgoService.doFakeDeleteByIds(loginUser, Lists.newArrayList(delIds));
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,SmartFormTypeDefinitionFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


}
