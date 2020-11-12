package com.egg.manager.web.controller.message.email;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.message.basic.email.EmailSendRecordMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBean;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.persistence.em.message.db.mongo.repository.email.EmailSendRecordRepository;
import com.egg.manager.persistence.em.message.pojo.mapstruct.imap.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.em.message.pojo.verification.email.EmailSendRecordMongoVerifyO;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
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
 * @author zhoucj
 * @description邮件发送记录-Controller
 * @date 2020/10/21
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


    @PcWebQueryLog(fullPath = "/message/email/emailSendRecord/getDataPage")
    @ApiOperation(value = "分页查询->邮件记录", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<EmailSendRecordMgo> doGetDataPage(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<EmailSendRecordMgo> result = MyCommonResult.gainQueryResult(EmailSendRecordMgo.class);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.IsDeleted_Eq_Not)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<EmailSendRecordMgo> pageBean = emailSendRecordMgoService.doFindPage(loginUserInfo, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(fullPath = "/message/email/emailSendRecord/getOneItemById")
    @ApiOperation(value = "根据id查询->邮件记录", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public MyCommonResult<EmailSendRecordMgo> doGetOneItemById(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo,
                                                               @RequestParam(value = "fid", required = true) String fid) {
        MyCommonResult<EmailSendRecordMgo> result = MyCommonResult.gainQueryResult(EmailSendRecordMgo.class);
        try {
            Assert.notNull(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
            EmailSendRecordMgo mobj = emailSendRecordMgoService.doFindById(loginUserInfo, fid);
            result.setBean(mobj);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/message/email/emailSendRecord/addByForm")
    @ApiOperation(value = "新增->邮件记录", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public MyCommonResult doAddByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo,
                                      @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) EmailSendRecordMongoVerifyO emailSendRecordMongoVerifyO,
                                      EmailSendRecordMgvo emailSendRecordMgvo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(emailSendRecordMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            EmailSendRecordMgo emailSendRecordMgo = EmailSendRecordMapstruct.INSTANCE.translateMgvoToMgo(emailSendRecordMgvo);
            EmailSendRecordMgo newMgo = emailSendRecordMgoService.doInsert(loginUserInfo, emailSendRecordMgo);
            addCount += (newMgo != null) ? 1 : 0;
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/message/email/emailSendRecord/delOneById")
    @ApiOperation(value = "伪删除->邮件记录", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要伪删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public MyCommonResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notNull(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
            Long delCount = emailSendRecordMgoService.doFakeDeleteById(loginUserInfo, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/message/email/emailSendRecord/batchDelByIds")
    @ApiOperation(value = "批量伪删除->邮件记录", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要伪删除的id数组", required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public MyCommonResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Long delCount = (long) 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = emailSendRecordMgoService.doFakeDeleteByIds(loginUserInfo, Lists.newArrayList(delIds));
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
