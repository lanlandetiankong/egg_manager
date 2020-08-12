package com.egg.manager.web.controller.forms.smartForm;

import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.query.MongoQueryBean;
import com.egg.manager.common.base.query.MyMongoQueryBuffer;
import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormDefinitionMService;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.mongo.mapstruct.forms.SmartFormDefinitionMapstruct;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.mongo.mvo.forms.SmartFormDefinitionMVO;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfCreate;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfDefault;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfUpdate;
import com.egg.manager.web.verification.mongodb.forms.smartForm.SmartFormDefinitionVerifyO;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private SmartFormDefinitionMService smartFormDefinitionMService;



    @OperLog(modelName = "SmartFormDefinitionController", action = "分页查询->表单定义", description = "")
    @ApiOperation(value = "分页查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<SmartFormDefinitionMO> doGetDataPage(HttpServletRequest request,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormDefinitionMO> result = new MyCommonResult();
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                                    .getRefreshedSelf();
            MongoQueryBean queryBean = MongoQueryBean.getMongoQueryBeanFromRequest(request,mongoQueryBuffer);
            queryBean.appendQueryFieldsToQuery(mongoQueryBuffer);
            Page<SmartFormDefinitionMO> page = smartFormDefinitionMService.doFindPage(loginUser,queryBean);
            dealSetMongoPageResult(result,page,"查询表单定义信息-Dto列表:"+actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @OperLog(modelName = "SmartFormDefinitionController", action = "分页查询->表单定义", description = "")
    @ApiOperation(value = "分页查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataAll")
    public MyCommonResult<SmartFormDefinitionMO> doGetDataAll(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormDefinitionMO> result = new MyCommonResult();
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            MongoQueryBean queryBean = MongoQueryBean.getMongoQueryBeanFromRequest(request,mongoQueryBuffer);
            queryBean.appendQueryFieldsToQuery(mongoQueryBuffer);
            Page<SmartFormDefinitionMO> page = smartFormDefinitionMService.doFindPage(loginUser,queryBean);
            dealSetMongoPageResult(result,page,"查询表单定义信息-Dto列表:"+actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @OperLog(modelName = "SmartFormDefinitionController", action = "根据id查询->表单定义", description = "")
    @ApiOperation(value = "根据id查询->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<SmartFormDefinitionMO> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                            @RequestParam(value="fid",required = true) String fid) {
        MyCommonResult<SmartFormDefinitionMO> result = new MyCommonResult();
        try {
            Optional<SmartFormDefinitionMO> moOptional = smartFormDefinitionMService.doFindById(loginUser,fid);
            result.setBean(moOptional.get());
            dealCommonSuccessCatch(result, "根据id查询->表单定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @OperLog(modelName = "SmartFormDefinitionController", action = "新增->表单定义", description = "")
    @ApiOperation(value = "新增->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/addByForm")
    public MyCommonResult<SmartFormDefinitionMO> doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormDefinitionVerifyO formDefinitionVerifyO,
                                                                 SmartFormDefinitionMVO formDefinitionMVO) {
        MyCommonResult<SmartFormDefinitionMO> result = new MyCommonResult();
        Integer addCount = 0;
        try {
            if (formDefinitionMVO == null) {
                throw new Exception("未接收到有效的表单定义！");
            } else {
                Optional<SmartFormTypeDefinitionMO> formTypeDefinitionMOOptional = smartFormTypeDefinitionRepository.findById(formDefinitionMVO.getFormTypeId());
                if(formTypeDefinitionMOOptional.isPresent() == false){
                    throw new BusinessException("不是有效的表单类型！");
                }
                SmartFormDefinitionMO formDefinitionMO = SmartFormDefinitionMapstruct.INSTANCE.mvo_CopyTo_MO(formDefinitionMVO);
                formDefinitionMO.setFormType(formTypeDefinitionMOOptional.get());
                SmartFormDefinitionMO newMO = smartFormDefinitionMService.doInsert(loginUser,formDefinitionMO);
                addCount += (newMO != null) ? 1 : 0;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "新增->表单定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "SmartFormDefinitionController", action = "更新->表单定义", description = "")
    @ApiOperation(value = "更新->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult<SmartFormDefinitionMO> doUpdateByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormDefinitionVerifyO formDefinitionVerifyO,
                                                                    SmartFormDefinitionMVO formDefinitionMVO) {
        MyCommonResult<SmartFormDefinitionMO> result = new MyCommonResult();
        Integer addCount = 0;
        try {
            if (formDefinitionMVO == null) {
                throw new Exception("未接收到有效的表单定义！");
            } else {
                Optional<SmartFormTypeDefinitionMO> formTypeDefinitionMOOptional = smartFormTypeDefinitionRepository.findById(formDefinitionMVO.getFormTypeId());
                if(formTypeDefinitionMOOptional.isPresent() == false){
                    throw new BusinessException("不是有效的表单类型！");
                }
                SmartFormDefinitionMO formDefinitionMO = SmartFormDefinitionMapstruct.INSTANCE.mvo_CopyTo_MO(formDefinitionMVO);
                formDefinitionMO.setFormType(formTypeDefinitionMOOptional.get());
                SmartFormDefinitionMO newMO = smartFormDefinitionMService.doUpdateById(loginUser,formDefinitionMO);
                addCount += (newMO != null) ? 1 : 0;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "更新->表单定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "SmartFormDefinitionController", action = "删除->表单定义", description = "")
    @ApiOperation(value = "删除->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult<SmartFormDefinitionMO> doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Long delCount = smartFormDefinitionMService.doFakeDeleteById(loginUser,delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, "批量删除->表单定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "SmartFormDefinitionController", action = "批量删除->表单定义", description = "")
    @ApiOperation(value = "批量删除->表单定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult<SmartFormDefinitionMO> doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Long delCount = (long) 0;
        try {
            if (delIds != null && delIds.length > 0) {
                delCount = smartFormDefinitionMService.doFakeDeleteByIds(loginUser,Lists.newArrayList(delIds));
                dealCommonSuccessCatch(result, "批量删除->表单定义:" + actionSuccessMsg);
            }
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}