package com.egg.manager.obl.web.controller.article;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleViewRecordService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleViewRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
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
 * @description 文章查看记录-Api
 * @date 2020-12-04
 */
@Slf4j
@Api(value = "API-文章查看记录")
@RestController
@RequestMapping("/oblArticleViewRecord")
public class OblArticleViewRecordController extends BaseController {

    @Autowired
    private OblArticleViewRecordMapper oblArticleViewRecordMapper;
    @Reference
    private OblArticleViewRecordService oblArticleViewRecordService;


    @ApiOperation(value = "分页查询(dto)->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebQueryLog(fullPath = "/oblArticleViewRecord/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblArticleViewRecordDto.class) QueryPageBean<OblArticleViewRecordDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = oblArticleViewRecordService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }
}