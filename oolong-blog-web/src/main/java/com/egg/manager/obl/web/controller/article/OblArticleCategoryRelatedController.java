package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleCategoryRelatedService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCategoryRelatedMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleCategoryRelatedTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryRelatedVo;
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
 * @description 文章分类关联表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-文章分类关联表")
@RestController
@RequestMapping("/oblArticleCategoryRelated")
public class OblArticleCategoryRelatedController extends BaseController {

    @Autowired
    private OblArticleCategoryRelatedMapper oblArticleCategoryRelatedMapper;
    @Reference
    private OblArticleCategoryRelatedService oblArticleCategoryRelatedService;


    @ApiOperation(value = "分页查询(dto)->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblArticleCategoryRelated/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblArticleCategoryRelatedDto.class) QueryPageBean<OblArticleCategoryRelatedDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblArticleCategoryRelatedService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblArticleCategoryRelated/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblArticleCategoryRelatedId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblArticleCategoryRelatedId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblArticleCategoryRelatedEntity oblArticleCategoryRelatedEntity = oblArticleCategoryRelatedMapper.selectById(oblArticleCategoryRelatedId);
        result.putBean(OblArticleCategoryRelatedTransfer.transferEntityToVo(oblArticleCategoryRelatedEntity));
        return result;
    }

    @ApiOperation(value = "新增->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblArticleCategoryRelated/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblArticleCategoryRelatedVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblArticleCategoryRelatedService.dealCreate(loginUserInfo, oblArticleCategoryRelatedVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblArticleCategoryRelated/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblArticleCategoryRelatedVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblArticleCategoryRelatedService.dealUpdate(loginUserInfo, oblArticleCategoryRelatedVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblArticleCategoryRelated/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblArticleCategoryRelatedService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblArticleCategoryRelated/deleteById")
    @ApiOperation(value = "伪删除->文章分类关联表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblArticleCategoryRelatedService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}