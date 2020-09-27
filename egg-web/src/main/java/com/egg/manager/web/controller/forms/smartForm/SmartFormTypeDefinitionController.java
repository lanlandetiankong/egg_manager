package com.egg.manager.web.controller.forms.smartForm;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.announcement.AnnouncementFuncModuleConstant;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormDefinitionMService;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormTypeDefinitionMService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.query.mongo.MongoQueryBean;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.forms.smartForm.SmartFormTypeDefinitionMongoVerifyO;
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
@Api(value = "API ==>>  HelloController ", description = "Index")
@RestController
@RequestMapping("/forms/smartForm/formTypeDefinition")
public class SmartFormTypeDefinitionController extends BaseController {

    @Reference
    private SmartFormTypeDefinitionMService smartFormTypeDefinitionMService;
    @Reference
    private SmartFormDefinitionMService smartFormDefinitionMService;


    @PcWebQueryLog(action = "分页查询->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<SmartFormTypeDefinitionMO> doGetDataPage(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = MyCommonResult.gainUniversalResult(SmartFormTypeDefinitionMO.class, AnnouncementFuncModuleConstant.Success.queryPage);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<SmartFormTypeDefinitionMO> pageBean = smartFormTypeDefinitionMService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean, "查询表单类型定义信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(action = "分页查询->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public MyCommonResult<SmartFormTypeDefinitionMO> doGetDataAll(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = MyCommonResult.gainUniversalResult(SmartFormTypeDefinitionMO.class,AnnouncementFuncModuleConstant.Success.queryPage);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            List<SmartFormTypeDefinitionMO> list = smartFormTypeDefinitionMService.doFindAll(loginUser, mongoQueryBuffer);
            result = smartFormTypeDefinitionMService.dealResultListToEnums(result, list);
            dealCommonSuccessCatch(result, "查询表单类型信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(action = "根据id查询->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<SmartFormTypeDefinitionMO> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                      @RequestParam(value = "fid", required = true) String fid) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = MyCommonResult.gainUniversalResult(SmartFormTypeDefinitionMO.class,AnnouncementFuncModuleConstant.Success.queryOneById);
        try {
            Assert.notBlank(fid,BaseRstMsgConstant.ErrorMsg.unknowId());
            SmartFormTypeDefinitionMO mobj = smartFormTypeDefinitionMService.doFindById(loginUser, fid);
            result.setBean(mobj);
            dealCommonSuccessCatch(result, "根据id查询->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;

    }

    @PcWebOperationLog(action = "新增->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/addByForm")
    @ApiOperation(value = "新增->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/addByForm")
    public MyCommonResult<SmartFormTypeDefinitionMO> doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                                                 SmartFormTypeDefinitionMO formTypeDefinitionMO) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = MyCommonResult.gainUniversalResult(SmartFormTypeDefinitionMO.class,AnnouncementFuncModuleConstant.Success.create);
        Integer addCount = 0;
        try {
            Assert.notNull(formTypeDefinitionMO, BaseRstMsgConstant.ErrorMsg.emptyForm());
            SmartFormTypeDefinitionMO newMO = smartFormTypeDefinitionMService.doInsert(loginUser, formTypeDefinitionMO);
            addCount += (newMO != null) ? 1 : 0;
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "新增->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "更新->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/updateByForm")
    @ApiOperation(value = "更新->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult<SmartFormTypeDefinitionMO> doUpdateByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormTypeDefinitionMongoVerifyO formTypeDefinitionVerifyO,
                                                                    SmartFormTypeDefinitionMO formTypeDefinitionMO) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = MyCommonResult.gainUniversalResult(SmartFormTypeDefinitionMO.class,AnnouncementFuncModuleConstant.Success.update);
        Integer count = 0;
        try {
            Assert.notNull(formTypeDefinitionMO,BaseRstMsgConstant.ErrorMsg.emptyForm());
            SmartFormTypeDefinitionMO newMO = smartFormTypeDefinitionMService.doUpdateById(loginUser, formTypeDefinitionMO);
            //更新了一条数据
            if (newMO != null) {
                count += 1;
                //触发formDefinetion 更新
                smartFormDefinitionMService.updateFormTypeByTypeId(loginUser, newMO);
            }
            result.setCount(count);
            dealCommonSuccessCatch(result, "更新->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/delOneById")
    @ApiOperation(value = "删除->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult<Object> doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<Object> result = MyCommonResult.gainUniversalResult(Object.class,AnnouncementFuncModuleConstant.Success.deleteById);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = smartFormTypeDefinitionMService.doFakeDeleteById(loginUser, delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, "批量删除->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除->表单类型定义", description = "", fullPath = "/forms/smartForm/formTypeDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult<Object> doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<Object> result = MyCommonResult.gainUniversalResult(Object.class,AnnouncementFuncModuleConstant.Success.batchDeleteByIds);
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = smartFormTypeDefinitionMService.doFakeDeleteByIds(loginUser, Lists.newArrayList(delIds));
            dealCommonSuccessCatch(result, "批量删除->表单类型定义:" + actionSuccessMsg);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
