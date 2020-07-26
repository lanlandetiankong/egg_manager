package com.egg.manager.web.controller.forms.smartForm;

import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormTypeDefinitionMService;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfCreate;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfDefault;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfUpdate;
import com.egg.manager.web.verification.mongodb.forms.smartForm.SmartFormTypeDefinitionVerifyO;
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
import java.util.List;
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
@RequestMapping("/forms/smartForm/formTypeDefinition")
public class SmartFormTypeDefinitionController extends BaseController {

    @Autowired
    private SmartFormTypeDefinitionRepository smartFormTypeDefinitionRepository;

    @Autowired
    private SmartFormTypeDefinitionMService smartFormTypeDefinitionMService;


    @OperLog(modelName = "FormTypeDefinitionController", action = "分页查询->表单类型定义", description = "")
    @ApiOperation(value = "分页查询->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = false, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<SmartFormTypeDefinitionMO> doGetDataPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                   @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = new MyCommonResult();
        try {
            List<SmartFormTypeDefinitionMO> list  =smartFormTypeDefinitionMService.doFindAll();
            result.setResultList(list);
            dealCommonSuccessCatch(result,"查询模块定义信息-Dto列表:"+actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @OperLog(modelName = "FormTypeDefinitionController", action = "根据id查询->表单类型定义", description = "")
    @ApiOperation(value = "根据id查询->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<SmartFormTypeDefinitionMO> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                            @RequestParam(value="fid",required = true) String fid) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = new MyCommonResult();
        try {
            Optional<SmartFormTypeDefinitionMO> formTypeDefinitionMOOptional = smartFormTypeDefinitionMService.doFindById(fid);
            result.setBean(formTypeDefinitionMOOptional.get());
            dealCommonSuccessCatch(result, "根据id查询->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;

    }

    @OperLog(modelName = "FormTypeDefinitionController", action = "新增->表单类型定义", description = "")
    @ApiOperation(value = "新增->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/addByForm")
    public MyCommonResult<SmartFormTypeDefinitionMO> doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) SmartFormTypeDefinitionVerifyO formTypeDefinitionVerifyO,
                                                                 SmartFormTypeDefinitionMO formTypeDefinitionMO) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = new MyCommonResult();
        Integer addCount = 0;
        try {
            if (formTypeDefinitionMO == null) {
                throw new Exception("未接收到有效的表单类型定义！");
            } else {
                SmartFormTypeDefinitionMO newMO = smartFormTypeDefinitionMService.doInsert(formTypeDefinitionMO, loginUser);
                addCount += (newMO != null) ? 1 : 0;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "新增->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "FormTypeDefinitionController", action = "更新->表单类型定义", description = "")
    @ApiOperation(value = "更新->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult<SmartFormTypeDefinitionMO> doUpdateByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                                    @Validated({VerifyGroupOfDefault.class, VerifyGroupOfUpdate.class}) SmartFormTypeDefinitionVerifyO formTypeDefinitionVerifyO,
                                                                    SmartFormTypeDefinitionMO formTypeDefinitionMO) {
        MyCommonResult<SmartFormTypeDefinitionMO> result = new MyCommonResult();
        Integer addCount = 0;
        try {
            if (formTypeDefinitionMO == null) {
                throw new Exception("未接收到有效的表单类型定义！");
            } else {
                SmartFormTypeDefinitionMO newMO = smartFormTypeDefinitionMService.doSave(formTypeDefinitionMO, loginUser);
                addCount += (newMO != null) ? 1 : 0;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "更新->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "FormTypeDefinitionController", action = "删除->表单类型定义", description = "")
    @ApiOperation(value = "删除->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult<SmartFormTypeDefinitionMO> doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Integer delCount = smartFormTypeDefinitionMService.doFakeDeleteById(delId, loginUser);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, "批量删除->表单类型定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @OperLog(modelName = "FormTypeDefinitionController", action = "批量删除->表单类型定义", description = "")
    @ApiOperation(value = "批量删除->表单类型定义", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult<SmartFormTypeDefinitionMO> doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            if (delIds != null && delIds.length > 0) {
                delCount = smartFormTypeDefinitionMService.doFakeDeleteByIds(Lists.newArrayList(delIds), loginUser);
                dealCommonSuccessCatch(result, "批量删除->表单类型定义:" + actionSuccessMsg);
            }
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
