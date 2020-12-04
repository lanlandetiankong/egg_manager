package com.egg.manager.em.web.controller.announcement;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
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
@Api(value = "API-发布公告接口")
@RestController
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController {
    @Autowired
    private AnnouncementMapper announcementMapper;
    @Reference
    private AnnouncementService announcementService;
    @Reference
    private AnnouncementTagService announcementTagService;

    @EmPcWebQueryLog(fullPath = "/announcement/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = AnnouncementDto.class) QueryPageBean<AnnouncementDto> queryPageBean,
                                  Boolean onlySelf, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        if (Boolean.TRUE.equals(onlySelf)) {
            //只查询自己发布的公告
            queryPageBean.operateQuery().addEq(FieldConst.COL_CREATE_USER_ID, loginUserInfo.getFid());
        }
        result = announcementService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "筛选查询->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/announcement/queryFilteredPage")
    @PostMapping(value = "/queryFilteredPage")
    public WebResult queryFilteredPage(HttpServletRequest request, Integer limitSize, @QueryPage(tClass = AnnouncementDto.class) QueryPageBean<AnnouncementDto> queryPageBean,
                                       Boolean onlySelf, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        //只查询自己发布的公告
        queryPageBean.operateQuery().addEq(onlySelf, FieldConst.COL_CREATE_USER_ID, loginUserInfo.getFid());
        queryPageBean.operatePageConf().setPageSize(limitSize);
        result = announcementService.dealQueryPageByEntitys(loginUserInfo, result, queryPageBean);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/announcement/queryOneById")
    @ApiOperation(value = "根据id查询->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String announcementId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(announcementId, BaseRstMsgConstant.ErrorMsg.unknowId());
        AnnouncementEntity announcementEntity = announcementMapper.selectById(announcementId);
        //取得 公告标签 map
        Map<String, AnnouncementTagEntity> announcementTagMap = announcementTagService.dealGetAllToMap();
        result.putBean(AnnouncementTransfer.transferEntityToVo(announcementEntity, announcementTagMap));
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/announcement/createByForm")
    @ApiOperation(value = "新增->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, AnnouncementVo announcementVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(announcementVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = announcementService.dealCreate(loginUserInfo, announcementVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "发布->公告草稿", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcement/createFromDraft")
    @PostMapping(value = "/createFromDraft")
    public WebResult createFromDraft(HttpServletRequest request, AnnouncementDraftVo announcementDraftVo,
                                     @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(announcementDraftVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = announcementService.dealCreateFromDraft(loginUserInfo, announcementDraftVo);
        result.putCount(addCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/announcement/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = announcementService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @ApiOperation(value = "伪删除->公告", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/announcement/deleteById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = announcementService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
