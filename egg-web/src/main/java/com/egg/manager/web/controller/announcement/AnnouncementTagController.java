package com.egg.manager.web.controller.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.service.annotation.log.CurrentLoginUser;
import com.egg.manager.api.service.annotation.log.OperLog;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.announcement.AnnouncementTagService;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.persistence.transfer.announcement.AnnouncementTagTransfer;
import com.egg.manager.persistence.vo.announcement.AnnouncementTagVo;
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
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  AnnouncementTagController ",description = "公告标签接口")
@RestController
@RequestMapping("/announcement_tag")
public class AnnouncementTagController extends BaseController {

    @Autowired
    private AnnouncementTagMapper announcementTagMapper ;

    @Reference
    private CommonFuncService commonFuncService ;
    @Reference
    private AnnouncementTagService announcementTagService ;


    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息Select列表",description = "查询公告标签信息Select列表")
    @ApiOperation(value = "查询公告标签信息Select列表", notes = "查询公告标签信息Select列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllAnnouncementTagEnums")
    public MyCommonResult<AnnouncementTagVo> doGetAllAnnouncementTagEnums(HttpServletRequest request,String queryObj, String paginationObj, String sortObj,
                                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>() ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            announcementTagService.dealGetAnnouncementTagPages(result,queryFieldBeanList,null,sortBeans) ;
            announcementTagService.dealResultListSetToEntitySelect(result) ;
            dealCommonSuccessCatch(result,"查询公告标签信息Select列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询公告标签信息-Dto列表", notes = "查询公告标签信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息-Dto列表",description = "查询公告标签信息-Dto列表")
    @PostMapping(value = "/getAllAnnouncementTagDtos")
    public MyCommonResult<AnnouncementTagVo> doGetAllAnnouncementTagDtos(HttpServletRequest request,String queryObj, String paginationObj, String sortObj,
                                                                         @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            announcementTagService.dealGetAnnouncementTagDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询公告标签信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询公告标签信息", notes = "根据公告标签id查询公告标签信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息",description = "根据公告标签id查询公告标签信息")
    @PostMapping(value = "/getAnnouncementTagById")
    public MyCommonResult<AnnouncementTagVo> doGetAnnouncementTagById(HttpServletRequest request,String announcementTagId,
                                                                      @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            AnnouncementTag announcementTag = announcementTagMapper.selectById(announcementTagId);
            result.setBean(AnnouncementTagTransfer.transferEntityToVo(announcementTag));
            dealCommonSuccessCatch(result,"查询公告标签信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增公告标签", notes = "表单方式新增公告标签", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementTagController",action="新增公告标签",description = "表单方式新增公告标签")
    @PostMapping(value = "/doAddAnnouncementTag")
    public MyCommonResult<AnnouncementTagVo> doAddAnnouncementTag(HttpServletRequest request,AnnouncementTagVo AnnouncementTagVo,
                                                                  @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        Integer addCount = 0 ;
        try{
            if(AnnouncementTagVo == null) {
                throw new Exception("未接收到有效的公告标签！");
            }   else {
                addCount = announcementTagService.dealAddAnnouncementTag(AnnouncementTagVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增公告标签:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新公告标签", notes = "表单方式更新公告标签", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="AnnouncementTagController",action="更新公告标签",description = "表单方式更新公告标签")
    @PostMapping(value = "/doUpdateAnnouncementTag")
    public MyCommonResult doUpdateAnnouncementTag(HttpServletRequest request,AnnouncementTagVo AnnouncementTagVo,
                                                  @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(AnnouncementTagVo == null) {
                throw new Exception("未接收到有效的公告标签！");
            }   else {
                changeCount = announcementTagService.dealUpdateAnnouncementTag(AnnouncementTagVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新公告标签:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="AnnouncementTagController",action="批量删除公告标签",description = "根据公告标签id批量删除公告标签")
    @ApiOperation(value = "批量删除公告标签", notes = "根据公告标签id批量删除公告标签", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的公告标签id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelAnnouncementTagByIds")
    public MyCommonResult doBatchDeleteAnnouncementTagById(HttpServletRequest request,String[] delIds,
                                                           @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = announcementTagService.dealDelAnnouncementTagByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除公告标签:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="AnnouncementTagController",action="删除公告标签",description = "根据id删除公告标签")
    @ApiOperation(value = "删除公告标签", notes = "根据id删除公告标签", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的公告标签id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneAnnouncementTagById")
    public MyCommonResult doDelOneAnnouncementTagById(HttpServletRequest request,String delId,
                                                      @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = announcementTagService.dealDelAnnouncementTag(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除公告标签:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

}
