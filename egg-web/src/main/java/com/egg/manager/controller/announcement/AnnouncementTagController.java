package com.egg.manager.controller.announcement;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.beans.FrontEntitySelectBean;
import com.egg.manager.common.base.beans.FrontSelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.announcement.AnnouncementTagService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.vo.announcement.AnnouncementTagVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RestController
@RequestMapping("/announcement_tag")
public class AnnouncementTagController extends BaseController {

    @Autowired
    private AnnouncementTagMapper announcementTagMapper ;

    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private AnnouncementTagService announcementTagService ;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());






    @ApiOperation(value = "查询公告标签信息Select列表", notes = "查询公告标签信息Select列表", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息Select列表",description = "查询公告标签信息Select列表")
    @PostMapping(value = "/getAllAnnouncementTagEnums")
    public MyCommonResult<AnnouncementTagVo> doGetAllAnnouncementTagEnums(HttpServletRequest request, HttpServletResponse response,String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>() ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            announcementTagService.dealGetAnnouncementTagPages(result,queryFieldBeanList,null,sortBeans) ;
            announcementTagService.dealResultListSetToEntitySelect(result) ;
            dealCommonSuccessCatch(result,"查询公告标签信息Select列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询公告标签信息列表", notes = "查询公告标签信息列表", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息列表",description = "查询公告标签信息列表")
    @PostMapping(value = "/getAllAnnouncementTags")
    public MyCommonResult<AnnouncementTagVo> doGetAllAnnouncementTags(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj, String sortObj) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            announcementTagService.dealGetAnnouncementTagPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询公告标签信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询公告标签信息", notes = "根据公告标签id查询公告标签信息", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="查询公告标签信息",description = "根据公告标签id查询公告标签信息")
    @PostMapping(value = "/getAnnouncementTagById")
    public MyCommonResult<AnnouncementTagVo> doGetAnnouncementTagById(HttpServletRequest request, HttpServletResponse response,String announcementTagId) {
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            AnnouncementTag announcementTag = announcementTagMapper.selectById(announcementTagId);
            result.setBean(AnnouncementTagVo.transferEntityToVo(announcementTag));
            dealCommonSuccessCatch(result,"查询公告标签信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增公告标签", notes = "表单方式新增公告标签", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="新增公告标签",description = "表单方式新增公告标签")
    @PostMapping(value = "/doAddAnnouncementTag")
    public MyCommonResult<AnnouncementTagVo> doAddAnnouncementTag(HttpServletRequest request, HttpServletResponse response,AnnouncementTagVo AnnouncementTagVo){
        MyCommonResult<AnnouncementTagVo> result = new MyCommonResult<AnnouncementTagVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(AnnouncementTagVo == null) {
                throw new Exception("未接收到有效的公告标签！");
            }   else {
                addCount = announcementTagService.dealAddAnnouncementTag(AnnouncementTagVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增公告标签:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新公告标签", notes = "表单方式更新公告标签", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="更新公告标签",description = "表单方式更新公告标签")
    @PostMapping(value = "/doUpdateAnnouncementTag")
    public MyCommonResult doUpdateAnnouncementTag(HttpServletRequest request, HttpServletResponse response, AnnouncementTagVo AnnouncementTagVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(AnnouncementTagVo == null) {
                throw new Exception("未接收到有效的公告标签！");
            }   else {
                changeCount = announcementTagService.dealUpdateAnnouncementTag(AnnouncementTagVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新公告标签:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除公告标签", notes = "根据用户id批量删除公告标签", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="批量删除公告标签",description = "根据用户id批量删除公告标签")
    @PostMapping(value = "/batchDelAnnouncementTagByIds")
    public MyCommonResult doBatchDeleteAnnouncementTagById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = announcementTagService.dealDelAnnouncementTagByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除公告标签:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除公告标签", notes = "根据id删除公告标签", response = String.class)
    @OperLog(modelName="AnnouncementTagController",action="删除公告标签",description = "根据id删除公告标签")
    @PostMapping(value = "/delOneAnnouncementTagById")
    public MyCommonResult doDelOneAnnouncementTagById(HttpServletRequest request, HttpServletResponse response, String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = announcementTagService.dealDelAnnouncementTag(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除公告标签:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
