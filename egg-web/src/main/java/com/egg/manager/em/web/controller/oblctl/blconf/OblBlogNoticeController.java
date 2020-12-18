package com.egg.manager.em.web.controller.oblctl.blconf;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogNoticeService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogNoticeEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogNoticeMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogNoticeDto;
import com.egg.manager.persistence.obl.blconf.pojo.transfer.OblBlogNoticeTransfer;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogNoticeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description 博客通知表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-博客通知表")
@RestController
@RequestMapping("/oblCtl/oblBlogNotice")
public class OblBlogNoticeController extends BaseController {

    @Autowired
    private OblBlogNoticeMapper oblBlogNoticeMapper;
    @Reference
    private OblBlogNoticeService oblBlogNoticeService;


    @ApiOperation(value = "分页查询(dto)->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblCtl/oblBlogNotice/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblBlogNoticeDto.class) QueryPageBean<OblBlogNoticeDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = oblBlogNoticeService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblCtl/oblBlogNotice/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblBlogNoticeId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblBlogNoticeId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblBlogNoticeEntity oblBlogNoticeEntity = oblBlogNoticeMapper.selectById(oblBlogNoticeId);
        result.putBean(OblBlogNoticeTransfer.transferEntityToVo(oblBlogNoticeEntity));
        return result;
    }

    @ApiOperation(value = "新增->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblCtl/oblBlogNotice/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblBlogNoticeVo oblBlogNoticeVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblBlogNoticeVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblBlogNoticeService.dealCreate(loginUserInfo, oblBlogNoticeVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblCtl/oblBlogNotice/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblBlogNoticeVo oblBlogNoticeVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblBlogNoticeVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblBlogNoticeService.dealUpdate(loginUserInfo, oblBlogNoticeVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblCtl/oblBlogNotice/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                      @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = oblBlogNoticeService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblCtl/oblBlogNotice/deleteById")
    @ApiOperation(value = "逻辑删除->博客通知表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblBlogNoticeService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}