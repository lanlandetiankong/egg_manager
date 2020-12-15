package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleLikeRecordService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleLikeRecordTransfer;
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
 * @description 文章点赞表-Api
 * @date 2020-12-02
 */
@Slf4j
@Api(value = "API-文章点赞表")
@RestController
@RequestMapping("/oblArticleLikeRecord")
public class OblArticleLikeRecordController extends BaseController {

    @Autowired
    private OblArticleLikeRecordMapper oblArticleLikeRecordMapper;
    @Reference
    private OblArticleLikeRecordService oblArticleLikeRecordService;


    @ApiOperation(value = "分页查询(dto)->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblArticleLikeRecord/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblArticleLikeRecordDto.class) QueryPageBean<OblArticleLikeRecordDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblArticleLikeRecordService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->文章点赞表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblArticleLikeRecord/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblArticleLikeRecordId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblArticleLikeRecordId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblArticleLikeRecordEntity oblArticleLikeRecordEntity = oblArticleLikeRecordMapper.selectById(oblArticleLikeRecordId);
        result.putBean(OblArticleLikeRecordTransfer.transferEntityToVo(oblArticleLikeRecordEntity));
        return result;
    }

}