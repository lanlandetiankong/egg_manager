package com.egg.manager.web.controller.announcement;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.persistence.transfer.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.announcement.AnnouncementDraftService;
import com.egg.manager.service.service.announcement.AnnouncementTagService;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  AnnouncementDraftController ",description = "公告草稿接口")
@RestController
@RequestMapping("/announcement_draft")
public class AnnouncementDraftController extends BaseController{

    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private AnnouncementDraftService announcementDraftService;
    @Autowired
    private AnnouncementTagService announcementTagService;

    @OperLog(modelName="AnnouncementDraftController",action="查询公告信息草稿-Dto列表",description = "查询公告信息草稿-Dto列表")
    @ApiOperation(value = "查询公告信息草稿-Dto列表", notes = "查询公告信息草稿-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllAnnouncementDraftDtos")
    public MyCommonResult<AnnouncementDraftVo> doGetAllAnnouncementDtos(HttpServletRequest request,String queryObj, String paginationObj, String sortObj,
                                                                        Boolean onlySelf,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = new MyCommonResult<AnnouncementDraftVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetNotEqualsBean("is_published", BaseStateEnum.ENABLED.getValue())) ;
            if(Boolean.TRUE.equals(onlySelf)){  //只查询自己发布的公告
                queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("create_user_id",loginUser.getFid() )) ;
            }
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            announcementDraftService.dealGetAnnouncementDraftDtoPages(result,queryFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询公告信息草稿-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询公告草稿信息", notes = "根据id查询公告草稿信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementDraftController",action="查询公告草稿信息",description = "根据id查询公告草稿信息")
    @PostMapping(value = "/getAnnouncementDraftById")
    public MyCommonResult<AnnouncementDraftVo> doGetAnnouncementDraftById(HttpServletRequest request,String draftId,
                                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementDraftVo> result = new MyCommonResult<AnnouncementDraftVo>() ;
        try{
            AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);
            //取得 公告标签 map
            Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
            result.setBean(AnnouncementDraftTransfer.transferEntityToVo(announcementDraft,announcementTagMap));
            dealCommonSuccessCatch(result,"查询公告草稿信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "新增公告草稿", notes = "表单方式新增公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementDraftController",action="新增公告草稿",description = "表单方式新增公告草稿")
    @PostMapping(value = "/addAnnouncementDraft")
    public MyCommonResult<AnnouncementDraftVo> doAddAnnouncementDraft(HttpServletRequest request,AnnouncementDraftVo announcementDraftVo,
                                                                      @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<AnnouncementDraftVo> result = new MyCommonResult<AnnouncementDraftVo>() ;
        Integer addCount = 0 ;
        try{
            if(announcementDraftVo == null) {
                throw new Exception("未接收到有效的公告草稿！");
            }   else {
                announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
                addCount = announcementDraftService.dealAddAnnouncementDraft(announcementDraftVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增公告草稿:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "更新公告草稿", notes = "表单方式更新公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementDraftController",action="更新公告草稿",description = "表单方式更新公告草稿")
    @PostMapping(value = "/updateAnnouncementDraft")
    public MyCommonResult<AnnouncementDraftVo> doUpdateAnnouncementDraft(HttpServletRequest request,AnnouncementDraftVo announcementDraftVo,
                                                                         @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<AnnouncementDraftVo> result = new MyCommonResult<AnnouncementDraftVo>() ;
        Integer updateCount = 0 ;
        try{
            if(announcementDraftVo == null) {
                throw new Exception("未接收到有效的公告草稿！");
            }   else {
                announcementDraftVo.setIsPublished(BaseStateEnum.DISABLED.getValue());
                updateCount = announcementDraftService.dealUpdateAnnouncementDraft(announcementDraftVo,loginUser) ;
            }
            result.setCount(updateCount);
            dealCommonSuccessCatch(result,"更新公告草稿:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }








    @OperLog(modelName="AnnouncementDraftController",action="批量删除公告草稿",description = "根据公告id批量删除公告草稿")
    @ApiOperation(value = "批量删除公告草稿", notes = "根据公告id批量删除公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的公告草稿id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelAnnouncementDraftByIds")
    public MyCommonResult doBatchDeleteAnnouncementById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = announcementDraftService.dealDelAnnouncementDraftByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除公告草稿:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="AnnouncementDraftController",action="删除公告草稿",description = "根据公告id删除公告草稿")
    @ApiOperation(value = "删除公告草稿", notes = "根据公告id删除公告草稿", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的公告草稿id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneAnnouncementDraftByIds")
    public MyCommonResult doDelOneAnnouncementDraftByIds(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = announcementDraftService.dealDelAnnouncementDraft(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除公告草稿:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "公告草稿批量转发布", notes = "根据公告草稿id批量发布", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementDraftController",action="公告草稿批量转发布",description = "根据公告草稿id批量发布")
    @PostMapping(value = "/batchPublishAnnouncementDraftByIds")
    public MyCommonResult doBatchPublishAnnouncementById(HttpServletRequest request,String[] draftIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer publishCount = 0;
        try{
            if(draftIds != null && draftIds.length > 0) {
                publishCount = announcementDraftService.dealPublishAnnouncementDraftByArr(draftIds,loginUser);
                dealCommonSuccessCatch(result,"公告草稿批量转发布:"+actionSuccessMsg);
            }
            result.setCount(publishCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "公告草稿转发布", notes = "根据公告草稿id批量转发布", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementDraftController",action="公告草稿转发布",description = "根据公告草稿id批量转发布")
    @PostMapping(value = "/publishOneAnnouncementDraftById")
    public MyCommonResult doPublishOneAnnouncementDraftById(HttpServletRequest request,String draftId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(draftId)){
                Integer publishCount = announcementDraftService.dealPublishAnnouncementDraft(draftId,loginUser,true);
                result.setCount(publishCount);
                dealCommonSuccessCatch(result,"发布公告草稿:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }
}