package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblUserCollectArticleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserCollectArticleMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserCollectArticleTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserCollectArticleVo;
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
 * @description 用户收藏的文章-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户收藏的文章")
@RestController
@RequestMapping("/oblUserCollectArticle")
public class OblUserCollectArticleController extends BaseController {

    @Autowired
    private OblUserCollectArticleMapper oblUserCollectArticleMapper;
    @Reference
    private OblUserCollectArticleService oblUserCollectArticleService;


    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.user.pojo.dto)->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserCollectArticle/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserCollectArticleDto.class) QueryPageBean<OblUserCollectArticleDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblUserCollectArticleService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserCollectArticle/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserCollectArticleId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserCollectArticleId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserCollectArticleEntity oblUserCollectArticleEntity = oblUserCollectArticleMapper.selectById(oblUserCollectArticleId);
        result.putBean(OblUserCollectArticleTransfer.transferEntityToVo(oblUserCollectArticleEntity));
        return result;
    }

    @ApiOperation(value = "新增->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserCollectArticle/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblUserCollectArticleVo oblUserCollectArticleVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblUserCollectArticleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblUserCollectArticleService.dealCreate(loginUserInfo, oblUserCollectArticleVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserCollectArticle/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblUserCollectArticleVo oblUserCollectArticleVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblUserCollectArticleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblUserCollectArticleService.dealUpdate(loginUserInfo, oblUserCollectArticleVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserCollectArticle/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblUserCollectArticleService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserCollectArticle/deleteById")
    @ApiOperation(value = "伪删除->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblUserCollectArticleService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}