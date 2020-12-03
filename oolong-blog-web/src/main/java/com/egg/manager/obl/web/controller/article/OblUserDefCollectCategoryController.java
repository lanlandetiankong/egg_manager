package com.egg.manager.obl.web.controller.article;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.OblUserDefCollectCategoryService;
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
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserDefCollectCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserDefCollectCategoryTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserDefCollectCategoryVo;
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
 * @description 用户定义的收藏类别-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户定义的收藏类别")
@RestController
@RequestMapping("/oblUserDefCollectCategory")
public class OblUserDefCollectCategoryController extends BaseController {

    @Autowired
    private OblUserDefCollectCategoryMapper oblUserDefCollectCategoryMapper;
    @Reference
    private OblUserDefCollectCategoryService oblUserDefCollectCategoryService;


    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.user.pojo.dto)->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblUserDefCollectCategory/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserDefCollectCategoryDto.class) QueryPageBean<OblUserDefCollectCategoryDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblUserDefCollectCategoryService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblUserDefCollectCategory/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserDefCollectCategoryId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserDefCollectCategoryId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserDefCollectCategoryEntity oblUserDefCollectCategoryEntity = oblUserDefCollectCategoryMapper.selectById(oblUserDefCollectCategoryId);
        result.putBean(OblUserDefCollectCategoryTransfer.transferEntityToVo(oblUserDefCollectCategoryEntity));
        return result;
    }

    @ApiOperation(value = "新增->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblUserDefCollectCategory/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblUserDefCollectCategoryVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblUserDefCollectCategoryService.dealCreate(loginUserInfo, oblUserDefCollectCategoryVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblUserDefCollectCategory/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblUserDefCollectCategoryVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblUserDefCollectCategoryService.dealUpdate(loginUserInfo, oblUserDefCollectCategoryVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblUserDefCollectCategory/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblUserDefCollectCategoryService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblUserDefCollectCategory/deleteById")
    @ApiOperation(value = "伪删除->用户定义的收藏类别", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblUserDefCollectCategoryService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}