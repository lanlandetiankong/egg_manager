package com.egg.manager.obl.web.controller.article;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblUserAttentionArticleCategoryService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserAttentionArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
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

}