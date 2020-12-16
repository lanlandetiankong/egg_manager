package com.egg.manager.em.web.controller.oblctl.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblContentLikeRecordService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblContentLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblContentLikeRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeRecordVo;
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
@RequestMapping("/oblContentLikeRecord")
public class OblContentLikeRecordController extends BaseController {

    @Autowired
    private OblContentLikeRecordMapper oblContentLikeRecordMapper;
    @Reference
    private OblContentLikeRecordService oblContentLikeRecordService;


    @ApiOperation(value = "分页查询(dto)->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblContentLikeRecord/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblContentLikeRecordDto.class) QueryPageBean<OblContentLikeRecordDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblContentLikeRecordService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblContentLikeRecord/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblContentLikeRecordId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblContentLikeRecordId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblContentLikeRecordEntity oblContentLikeRecordEntity = oblContentLikeRecordMapper.selectById(oblContentLikeRecordId);
        result.putBean(OblContentLikeRecordTransfer.transferEntityToVo(oblContentLikeRecordEntity));
        return result;
    }

    @ApiOperation(value = "新增->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblContentLikeRecord/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblContentLikeRecordVo oblContentLikeRecordVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblContentLikeRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblContentLikeRecordService.dealCreate(loginUserInfo, oblContentLikeRecordVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblContentLikeRecord/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblContentLikeRecordVo oblContentLikeRecordVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblContentLikeRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblContentLikeRecordService.dealUpdate(loginUserInfo, oblContentLikeRecordVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblContentLikeRecord/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblContentLikeRecordService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblContentLikeRecord/deleteById")
    @ApiOperation(value = "逻辑删除->评论点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblContentLikeRecordService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}