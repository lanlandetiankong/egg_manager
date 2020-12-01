package com.egg.manager.em.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTagTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementTagVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.api.exchange.BaseController;
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
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-公告标签接口")
@RestController
@RequestMapping("/announcementTag")
public class AnnouncementTagController extends BaseController {
    @Autowired
    private AnnouncementTagMapper announcementTagMapper;
    @Reference
    private AnnouncementTagService announcementTagService;

    @PcWebQueryLog(fullPath = "/announcementTag/gainEnumSelect")
    @ApiOperation(value = "分页查询->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/gainEnumSelect")
    public WebResult gainEnumSelect(HttpServletRequest request, @QueryPage() QueryPageBean queryPageBean,
                                    @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = announcementTagService.dealQueryPageByEntitys(loginUserInfo, result, queryPageBean);
        result = announcementTagService.dealResultListToEnums(result);
        return result;
    }

    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcementTag/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = AnnouncementTagDto.class) QueryPageBean<AnnouncementTagDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = announcementTagService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/announcementTag/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String announcementTagId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(announcementTagId, BaseRstMsgConstant.ErrorMsg.unknowId());
        AnnouncementTagEntity announcementTagEntity = announcementTagMapper.selectById(announcementTagId);
        result.putBean(AnnouncementTagTransfer.transferEntityToVo(announcementTagEntity));
        return result;
    }

    @ApiOperation(value = "新增->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementTag/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(announcementTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = announcementTagService.dealCreate(loginUserInfo, announcementTagVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/announcementTag/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, AnnouncementTagVo announcementTagVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(announcementTagVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = announcementTagService.dealUpdate(loginUserInfo, announcementTagVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/announcementTag/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = announcementTagService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/announcementTag/deleteById")
    @ApiOperation(value = "伪删除->公告标签", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = announcementTagService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
