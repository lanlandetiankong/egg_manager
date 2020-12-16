package com.egg.manager.em.web.controller.oblctl.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblUserAttentionArticleCategoryService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserAttentionArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserAttentionArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserAttentionArticleCategoryTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserAttentionArticleCategoryVo;
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
 * @description 用户关注的文章收藏类别-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户关注的文章收藏类别")
@RestController
@RequestMapping("/oblUserAttentionArticleCategory")
public class OblUserAttentionArticleCategoryController extends BaseController {

    @Autowired
    private OblUserAttentionArticleCategoryMapper oblUserAttentionArticleCategoryMapper;
    @Reference
    private OblUserAttentionArticleCategoryService oblUserAttentionArticleCategoryService;


    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.user.pojo.dto)->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserAttentionArticleCategory/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserAttentionArticleCategoryDto.class) QueryPageBean<OblUserAttentionArticleCategoryDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = oblUserAttentionArticleCategoryService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserAttentionArticleCategory/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserAttentionArticleCategoryId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserAttentionArticleCategoryId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserAttentionArticleCategoryEntity oblUserAttentionArticleCategoryEntity = oblUserAttentionArticleCategoryMapper.selectById(oblUserAttentionArticleCategoryId);
        result.putBean(OblUserAttentionArticleCategoryTransfer.transferEntityToVo(oblUserAttentionArticleCategoryEntity));
        return result;
    }

    @ApiOperation(value = "新增->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserAttentionArticleCategory/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblUserAttentionArticleCategoryVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblUserAttentionArticleCategoryService.dealCreate(loginUserInfo, oblUserAttentionArticleCategoryVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserAttentionArticleCategory/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblUserAttentionArticleCategoryVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblUserAttentionArticleCategoryService.dealUpdate(loginUserInfo, oblUserAttentionArticleCategoryVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserAttentionArticleCategory/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblUserAttentionArticleCategoryService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserAttentionArticleCategory/deleteById")
    @ApiOperation(value = "逻辑删除->用户关注的文章收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblUserAttentionArticleCategoryService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}