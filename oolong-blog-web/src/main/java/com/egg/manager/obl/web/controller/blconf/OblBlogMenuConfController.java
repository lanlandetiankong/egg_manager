package com.egg.manager.obl.web.controller.blconf;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogMenuConfService;
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
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogMenuConfMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import com.egg.manager.persistence.obl.blconf.pojo.transfer.OblBlogMenuConfTransfer;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogMenuConfVo;
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
 * @description 博客菜单定义表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-博客菜单定义表")
@RestController
@RequestMapping("/oblBlogMenuConf")
public class OblBlogMenuConfController extends BaseController {

    @Autowired
    private OblBlogMenuConfMapper oblBlogMenuConfMapper;
    @Reference
    private OblBlogMenuConfService oblBlogMenuConfService;


    @ApiOperation(value = "分页查询(dto)->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblBlogMenuConf/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblBlogMenuConfDto.class) QueryPageBean<OblBlogMenuConfDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblBlogMenuConfService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblBlogMenuConf/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblBlogMenuConfId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblBlogMenuConfId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblBlogMenuConfEntity oblBlogMenuConfEntity = oblBlogMenuConfMapper.selectById(oblBlogMenuConfId);
        result.putBean(OblBlogMenuConfTransfer.transferEntityToVo(oblBlogMenuConfEntity));
        return result;
    }

    @ApiOperation(value = "新增->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblBlogMenuConf/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblBlogMenuConfVo oblBlogMenuConfVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblBlogMenuConfVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblBlogMenuConfService.dealCreate(loginUserInfo, oblBlogMenuConfVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblBlogMenuConf/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblBlogMenuConfVo oblBlogMenuConfVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblBlogMenuConfVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblBlogMenuConfService.dealUpdate(loginUserInfo, oblBlogMenuConfVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblBlogMenuConf/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblBlogMenuConfService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblBlogMenuConf/deleteById")
    @ApiOperation(value = "逻辑删除->博客菜单定义表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblBlogMenuConfService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}