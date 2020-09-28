package com.egg.manager.web.controller.forms.smartForm;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcModule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.controllers.announcement.AnnouncementFuncModuleConstant;
import com.egg.manager.api.constants.funcModule.controllers.forms.smartForm.SmartFormDefinitionFuncModuleConstant;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormDefinitionMService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.query.mongo.MongoQueryBean;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormDefinitionMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormDefinitionMVO;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.forms.smartForm.SmartFormDefinitionMongoVerifyO;
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
@RequestMapping("/forms/smartForm/formDefinition")
public class SmartFormDefinitionController extends BaseController {

    @Autowired
    private SmartFormTypeDefinitionRepository smartFormTypeDefinitionRepository;

    @Reference
    private SmartFormDefinitionMService smartFormDefinitionMService;


    @PcWebQueryLog(action = "分页查询->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/getDataPage")
    @ApiOperation(value = "分页查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<SmartFormDefinitionMO> doGetDataPage(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormDefinitionMO> result = MyCommonResult.gainQueryResult(SmartFormDefinitionMO.class, SmartFormDefinitionFuncModuleConstant.Success.queryPage);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<SmartFormDefinitionMO> pageBean = smartFormDefinitionMService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }

    @PcWebQueryLog(action = "分页查询->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/getDataAll")
    @ApiOperation(value = "分页查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public MyCommonResult<SmartFormDefinitionMO> doGetDataAll(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormDefinitionMO> result = MyCommonResult.gainQueryResult(SmartFormDefinitionMO.class,SmartFormDefinitionFuncModuleConstant.Success.queryPage);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<SmartFormDefinitionMO> pageBean = smartFormDefinitionMService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }

    @PcWebQueryLog(action = "根据id查询->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/getOneItemById")
    @ApiOperation(value = "根据id查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<SmartFormDefinitionMO> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                  @RequestParam(value = "fid", required = true) String fid) {
        MyCommonResult<SmartFormDefinitionMO> result = MyCommonResult.gainQueryResult(SmartFormDefinitionMO.class,SmartFormDefinitionFuncModuleConstant.Success.queryOneById);
        try {
            Assert.notBlank(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
            SmartFormDefinitionMO mobj = smartFormDefinitionMService.doFindById(loginUser, fid);
            result.setBean(mobj);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.queryOneById);
        }
        return result;
    }

    @PcWebOperationLog(action = "新增->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/addByForm")
    @ApiOperation(value = "新增->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/addByForm")
    public MyCommonResult doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                             @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                                             SmartFormDefinitionMVO formDefinitionMVO) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormDefinitionFuncModuleConstant.Success.create);
        Integer addCount = 0;
        try {
            Assert.notNull(formDefinitionMVO,BaseRstMsgConstant.ErrorMsg.emptyForm());
            Optional<SmartFormTypeDefinitionMO> formTypeDefinitionMOOptional = smartFormTypeDefinitionRepository.findById(formDefinitionMVO.getFormTypeId());
            if (formTypeDefinitionMOOptional.isPresent() == false) {
                throw new BusinessException("不是有效的表单类型！");
            }
            SmartFormDefinitionMO formDefinitionMO = SmartFormDefinitionMapstruct.INSTANCE.translateMvoToMo(formDefinitionMVO);
            formDefinitionMO.setFormType(formTypeDefinitionMOOptional.get());
            SmartFormDefinitionMO newMO = smartFormDefinitionMService.doInsert(loginUser, formDefinitionMO);
            addCount += (newMO != null) ? 1 : 0;
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.create);
        }
        return result;
    }


    @PcWebOperationLog(action = "更新->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/updateByForm")
    @ApiOperation(value = "更新->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult doUpdateByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormDefinitionMongoVerifyO formDefinitionVerifyO,
                                                                SmartFormDefinitionMVO formDefinitionMVO) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormDefinitionFuncModuleConstant.Success.update);
        Integer addCount = 0;
        try {
            Assert.notNull(formDefinitionMVO,BaseRstMsgConstant.ErrorMsg.emptyForm());
            Optional<SmartFormTypeDefinitionMO> formTypeDefinitionMOOptional = smartFormTypeDefinitionRepository.findById(formDefinitionMVO.getFormTypeId());
            if (formTypeDefinitionMOOptional.isPresent() == false) {
                throw new BusinessException("不是有效的表单类型！");
            }
            SmartFormDefinitionMO formDefinitionMO = SmartFormDefinitionMapstruct.INSTANCE.translateMvoToMo(formDefinitionMVO);
            formDefinitionMO.setFormType(formTypeDefinitionMOOptional.get());
            SmartFormDefinitionMO newMO = smartFormDefinitionMService.doUpdateById(loginUser, formDefinitionMO);
            addCount += (newMO != null) ? 1 : 0;
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.update);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/delOneById")
    @ApiOperation(value = "删除->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormDefinitionFuncModuleConstant.Success.deleteById);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = smartFormDefinitionMService.doFakeDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.deleteById);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除->表单定义", description = "", fullPath = "/forms/smartForm/formDefinition/batchDelByIds")
    @ApiOperation(value = "批量删除->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(SmartFormDefinitionFuncModuleConstant.Success.batchDeleteByIds);
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = smartFormDefinitionMService.doFakeDeleteByIds(loginUser, Lists.newArrayList(delIds));
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,SmartFormDefinitionFuncModuleConstant.Failure.batchDeleteByIds);
        }
        return result;
    }


}
