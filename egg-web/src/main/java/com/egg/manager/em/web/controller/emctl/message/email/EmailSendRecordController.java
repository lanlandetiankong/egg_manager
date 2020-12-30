package com.egg.manager.em.web.controller.emctl.message.email;

import cn.hutool.core.lang.Assert;
import org.apache.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.message.basic.email.EmailSendRecordMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.constant.db.MongoFieldConstant;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.persistence.em.message.pojo.mapstruct.imap.email.EmailSendRecordMapstruct;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import com.egg.manager.persistence.em.message.pojo.verification.email.EmailSendRecordMongoVerifyO;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
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
 * @description邮件发送记录-Controller
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-邮件发送记录")
@RestController
@RequestMapping("/emCtl/message/email/emailSendRecord")
public class EmailSendRecordController extends BaseController {
    @Reference
    private EmailSendRecordMgoService emailSendRecordMgoService;

    @EmPcWebQueryLog(fullPath = "/emCtl/message/email/emailSendRecord/getDataPage")
    @ApiOperation(value = "分页查询->邮件记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @QueryPage(tClass = EmailSendRecordMgo.class) QueryPageBean<EmailSendRecordMgo> queryPageBean,
                                   @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Open.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        AntdvPage<EmailSendRecordMgo> pageBean = emailSendRecordMgoService.doFindPage(loginUserInfo, queryPageBean);
        result.putPage(pageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/message/email/emailSendRecord/getOneItemById")
    @ApiOperation(value = "根据id查询->邮件记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getOneItemById")
    public WebResult doGetOneItemById(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                      @RequestParam(value = FieldConst.FIELD_FID, required = true) String fid) {
        WebResult result = WebResult.okQuery();
        Assert.notNull(fid, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmailSendRecordMgo mobj = emailSendRecordMgoService.doFindById(loginUserInfo, fid);
        result.putBean(mobj);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/message/email/emailSendRecord/addByForm")
    @ApiOperation(value = "新增->邮件记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/addByForm")
    public WebResult doAddByForm(HttpServletRequest request, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo,
                                 @Validated({VerifyGroupOfDefault.class, VerifyGroupOfCreate.class}) EmailSendRecordMongoVerifyO emailSendRecordMongoVerifyO,
                                 EmailSendRecordMgvo emailSendRecordMgvo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emailSendRecordMgvo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        EmailSendRecordMgo emailSendRecordMgo = EmailSendRecordMapstruct.INSTANCE.translateMgvoToMgo(emailSendRecordMgvo);
        EmailSendRecordMgo newMgo = emailSendRecordMgoService.doInsert(loginUserInfo, emailSendRecordMgo);
        addCount += (newMgo != null) ? 1 : 0;
        result.putCount(addCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/message/email/emailSendRecord/delOneById")
    @ApiOperation(value = "逻辑删除->邮件记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要逻辑删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneById")
    public WebResult doDelOneById(HttpServletRequest request, @NotBlank String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notNull(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Long delCount = emailSendRecordMgoService.doLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/message/email/emailSendRecord/batchDelByIds")
    @ApiOperation(value = "批量逻辑删除->邮件记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要逻辑删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelByIds")
    public WebResult doBatchDelByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Long delCount = (long) 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emailSendRecordMgoService.doLogicDeleteByIds(loginUserInfo, Lists.newArrayList(delIds));
        result.putCount(delCount);
        return result;
    }
}
