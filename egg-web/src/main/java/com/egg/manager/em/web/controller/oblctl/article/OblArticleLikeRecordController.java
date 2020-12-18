package com.egg.manager.em.web.controller.oblctl.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleLikeRecordService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleLikeRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeRecordVo;
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
 * @description 文章点赞表-Api
 * @date 2020-12-02
 */
@Slf4j
@Api(value = "API-文章点赞表")
@RestController
@RequestMapping("/oblCtl/oblArticleLikeRecord")
public class OblArticleLikeRecordController extends BaseController {

    @Autowired
    private OblArticleLikeRecordMapper oblArticleLikeRecordMapper;
    @Reference
    private OblArticleLikeRecordService oblArticleLikeRecordService;


    @ApiOperation(value = "分页查询(dto)->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblCtl/oblArticleLikeRecord/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblArticleLikeRecordDto.class) QueryPageBean<OblArticleLikeRecordDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = oblArticleLikeRecordService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblCtl/oblArticleLikeRecord/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblArticleLikeRecordId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblArticleLikeRecordId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblArticleLikeRecordEntity oblArticleLikeRecordEntity = oblArticleLikeRecordMapper.selectById(oblArticleLikeRecordId);
        result.putBean(OblArticleLikeRecordTransfer.transferEntityToVo(oblArticleLikeRecordEntity));
        return result;
    }

    @ApiOperation(value = "新增->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblCtl/oblArticleLikeRecord/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblArticleLikeRecordVo oblArticleLikeRecordVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblArticleLikeRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblArticleLikeRecordService.dealCreate(loginUserInfo, oblArticleLikeRecordVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblCtl/oblArticleLikeRecord/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblArticleLikeRecordVo oblArticleLikeRecordVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblArticleLikeRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblArticleLikeRecordService.dealUpdate(loginUserInfo, oblArticleLikeRecordVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblCtl/oblArticleLikeRecord/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblArticleLikeRecordService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblCtl/oblArticleLikeRecord/deleteById")
    @ApiOperation(value = "逻辑删除->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblArticleLikeRecordService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}