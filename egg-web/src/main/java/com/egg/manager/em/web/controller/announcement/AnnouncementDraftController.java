package com.egg.manager.em.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementDraftService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementDraftMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementDraftTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-公告草稿接口 ")
@RestController
@RequestMapping("/announcementDraft")
public class AnnouncementDraftController extends BaseController {
    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;
    @Reference
    private AnnouncementDraftService announcementDraftService;
    @Reference
    private AnnouncementTagService announcementTagService;

    @EmPcWebQueryLog(fullPath = "/announcementDraft/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = AnnouncementDraftDto.class) QueryPageBean<AnnouncementDraftDto> queryPageBean,
                                  Boolean onlySelf, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addNotEq("is_published", BaseStateEnum.ENABLED.getValue());
        //只查询自己发布的公告
        queryPageBean.operateQuery().addEq(onlySelf, FieldConst.COL_CREATE_USER_ID, loginUserInfo.getFid());
        result = announcementDraftService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/announcementDraft/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String draftId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(draftId, BaseRstMsgConstant.ErrorMsg.unknowId());
        AnnouncementDraftEntity announcementDraftEntity = announcementDraftMapper.selectById(draftId);
        //取得 公告标签 map
        Map<String, AnnouncementTagEntity> announcementTagMap = announcementTagService.dealGetAllToMap();
        result.putBean(AnnouncementDraftTransfer.transferEntityToVo(announcementDraftEntity, announcementTagMap));
        return result;
    }

    @ApiOperation(value = "新增->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcementDraft/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
        addCount = announcementDraftService.dealCreate(loginUserInfo, announcementDraftVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcementDraft/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer updateCount = 0;
        Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
        updateCount = announcementDraftService.dealUpdate(loginUserInfo, announcementDraftVo);
        result.putCount(updateCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/announcementDraft/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = announcementDraftService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/announcementDraft/deleteById")
    @ApiOperation(value = "伪删除->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = announcementDraftService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @ApiOperation(value = "批量发布->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcementDraft/batchPublishDraft")
    @PostMapping(value = "/batchPublishDraft")
    public WebResult batchPublishDraft(HttpServletRequest request, String[] draftIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer publishCount = 0;
        Assert.notEmpty(draftIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        publishCount = announcementDraftService.dealBatchPublishByDraft(loginUserInfo, draftIds);
        result.putCount(publishCount);
        return result;
    }

    @ApiOperation(value = "发布->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcementDraft/publishDraft")
    @PostMapping(value = "/publishDraft")
    public WebResult publishDraft(HttpServletRequest request, String draftId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(draftId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer publishCount = announcementDraftService.dealPublishByDraft(loginUserInfo, draftId, true);
        result.putCount(publishCount);
        return result;
    }
}
