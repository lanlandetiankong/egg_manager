package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleViewRecordService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleViewRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleViewRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleViewRecordVo;
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
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblArticleViewRecordService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebQueryLog(fullPath = "/oblArticleViewRecord/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblArticleViewRecordId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblArticleViewRecordId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblArticleViewRecordEntity oblArticleViewRecordEntity = oblArticleViewRecordMapper.selectById(oblArticleViewRecordId);
        result.putBean(OblArticleViewRecordTransfer.transferEntityToVo(oblArticleViewRecordEntity));
        return result;
    }

    @ApiOperation(value = "新增->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblArticleViewRecord/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblArticleViewRecordVo oblArticleViewRecordVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblArticleViewRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblArticleViewRecordService.dealCreate(loginUserInfo, oblArticleViewRecordVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblArticleViewRecord/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblArticleViewRecordVo oblArticleViewRecordVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblArticleViewRecordVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblArticleViewRecordService.dealUpdate(loginUserInfo, oblArticleViewRecordVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblArticleViewRecord/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblArticleViewRecordService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblArticleViewRecord/deleteById")
    @ApiOperation(value = "伪删除->文章查看记录", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblArticleViewRecordService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}