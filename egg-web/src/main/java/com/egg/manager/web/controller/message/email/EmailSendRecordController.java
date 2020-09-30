package com.egg.manager.web.controller.message.email;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.adviser.MyControllerAdviserFuncModuleConstant;
import com.egg.manager.api.services.message.services.email.EmailSendRecordMgoService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.query.mongo.MongoQueryBean;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.message.email.EmailSendRecordMgo;
import com.egg.manager.persistence.db.mongo.repository.message.email.EmailSendRecordRepository;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.message.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.message.email.EmailSendRecordMongoVerifyO;
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

/**
 * @Description: 邮件发送记录-Controller
 * @ClassName: EmailSendRecordController
 * @Author: zhoucj
 * @Date: 2020/9/11 17:19
 */
@Slf4j
@Api(value = "API-邮件发送记录")
@RestController
@RequestMapping("/message/email/emailSendRecord")
public class EmailSendRecordController extends BaseController {
    @Autowired
    private EmailSendRecordRepository emailSendRecordRepository;

    @Reference
    private EmailSendRecordMgoService emailSendRecordMgoService;


    @PcWebQueryLog(action = "分页查询->邮件记录", fullPath = "/message/email/emailSendRecord/getDataPage")
    @ApiOperation(value = "分页查询->邮件记录", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 ->> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 ->> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<EmailSendRecordMgo> doGetDataPage(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<EmailSendRecordMgo> result = MyCommonResult.gainQueryResult(EmailSendRecordMgo.class,MyControllerAdviserFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<EmailSendRecordMgo> pageBean = emailSendRecordMgoService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,MyControllerAdviserFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @PcWebQueryLog(action = "根据id查询->邮件记录", fullPath = "/message/email/emailSendRecord/getOneItemById")
    @ApiOperation(value = "根据id查询->邮件记录", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<EmailSendRecordMgo> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                               @RequestParam(value = "fid", required = true) String fid) {
        MyCommonResult<EmailSendRecordMgo> result = MyCommonResult.gainQueryResult(EmailSendRecordMgo.class,MyControllerAdviserFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            Assert.notBlank(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
            EmailSendRecordMgo mobj = emailSendRecordMgoService.doFindById(loginUser, fid);
            result.setBean(mobj);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,MyControllerAdviserFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }

    @PcWebOperationLog(action = "新增->邮件记录", fullPath = "/message/email/emailSendRecord/addByForm")
    @ApiOperation(value = "新增->邮件记录", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/addByForm")
    public MyCommonResult doAddByForm(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser,
                                                         @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) EmailSendRecordMongoVerifyO emailSendRecordMongoVerifyO,
                                                         EmailSendRecordMgvo emailSendRecordMgvo) {
        MyCommonResult result = MyCommonResult.gainOperationResult(MyControllerAdviserFuncModuleConstant.Success.CREATE);
        Integer addCount = 0;
        try {
            Assert.notNull(emailSendRecordMgvo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            EmailSendRecordMgo emailSendRecordMgo = EmailSendRecordMapstruct.INSTANCE.translateMgvoToMgo(emailSendRecordMgvo);
            EmailSendRecordMgo newMO = emailSendRecordMgoService.doInsert(loginUser, emailSendRecordMgo);
            addCount += (newMO != null) ? 1 : 0;
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,MyControllerAdviserFuncModuleConstant.Failure.CREATE);
        }
        return result;
    }


    @PcWebOperationLog(action = "伪删除->邮件记录", fullPath = "/message/email/emailSendRecord/delOneById")
    @ApiOperation(value = "伪删除->邮件记录", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要伪删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(MyControllerAdviserFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = emailSendRecordMgoService.doFakeDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,MyControllerAdviserFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量伪删除->邮件记录", fullPath = "/message/email/emailSendRecord/batchDelByIds")
    @ApiOperation(value = "批量伪删除->邮件记录", notes = "", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要伪删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(MyControllerAdviserFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = emailSendRecordMgoService.doFakeDeleteByIds(loginUser, Lists.newArrayList(delIds));
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,MyControllerAdviserFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


}
