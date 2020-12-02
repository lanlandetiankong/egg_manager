package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.OblContentLikeLogService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeLogEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblContentLikeLogMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblContentLikeLogTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeLogVo;
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
 * @description 评论点赞表-Api
 * @date 2020-12-02
 */
@Slf4j
@Api(value = "API-评论点赞表")
@RestController
@RequestMapping("/oblContentLikeLog")
public class OblContentLikeLogController extends BaseController {

    @Autowired
    private OblContentLikeLogMapper oblContentLikeLogMapper;
    @Reference
    private OblContentLikeLogService oblContentLikeLogService;


    @ApiOperation(value = "分页查询(dto)->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblContentLikeLog/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblContentLikeLogDto.class) QueryPageBean<OblContentLikeLogDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblContentLikeLogService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblContentLikeLog/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblContentLikeLogId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblContentLikeLogId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblContentLikeLogEntity oblContentLikeLogEntity = oblContentLikeLogMapper.selectById(oblContentLikeLogId);
        result.putBean(OblContentLikeLogTransfer.transferEntityToVo(oblContentLikeLogEntity));
        return result;
    }

    @ApiOperation(value = "新增->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblContentLikeLog/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblContentLikeLogVo oblContentLikeLogVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblContentLikeLogVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblContentLikeLogService.dealCreate(loginUserInfo, oblContentLikeLogVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblContentLikeLog/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblContentLikeLogVo oblContentLikeLogVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblContentLikeLogVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblContentLikeLogService.dealUpdate(loginUserInfo, oblContentLikeLogVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblContentLikeLog/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                      @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = oblContentLikeLogService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblContentLikeLog/deleteById")
    @ApiOperation(value = "伪删除->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblContentLikeLogService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}