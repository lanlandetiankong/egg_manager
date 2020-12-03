package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.OblArticleTagService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleTagMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleTagTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagVo;
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
 * @description 文章标签定义表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-文章标签定义表")
@RestController
@RequestMapping("/oblArticleTag")
public class OblArticleTagController extends BaseController {

    @Autowired
    private OblArticleTagMapper oblArticleTagMapper;
    @Reference
    private OblArticleTagService oblArticleTagService;


    @ApiOperation(value = "分页查询(dto)->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblArticleTag/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblArticleTagDto.class) QueryPageBean<OblArticleTagDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblArticleTagService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblArticleTag/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblArticleTagId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblArticleTagId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblArticleTagEntity oblArticleTagEntity = oblArticleTagMapper.selectById(oblArticleTagId);
        result.putBean(OblArticleTagTransfer.transferEntityToVo(oblArticleTagEntity));
        return result;
    }

    @ApiOperation(value = "新增->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblArticleTag/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblArticleTagVo oblArticleTagVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblArticleTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblArticleTagService.dealCreate(loginUserInfo, oblArticleTagVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblArticleTag/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblArticleTagVo oblArticleTagVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblArticleTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblArticleTagService.dealUpdate(loginUserInfo, oblArticleTagVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblArticleTag/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblArticleTagService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblArticleTag/deleteById")
    @ApiOperation(value = "伪删除->文章标签定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblArticleTagService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}