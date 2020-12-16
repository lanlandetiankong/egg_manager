package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblUserCollectArticleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserCollectArticleMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserCollectArticleTransfer;
import io.swagger.annotations.Api;
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
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = oblUserCollectArticleService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户收藏的文章", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserCollectArticle/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserCollectArticleId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserCollectArticleId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserCollectArticleEntity oblUserCollectArticleEntity = oblUserCollectArticleMapper.selectById(oblUserCollectArticleId);
        result.putBean(OblUserCollectArticleTransfer.transferEntityToVo(oblUserCollectArticleEntity));
        return result;
    }

}