package com.egg.manager.web.controller.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.annotation.log.CurrentLoginUser;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.service.service.define.DefineJobService;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.entity.define.DefineJob;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.vo.define.DefineJobVo;
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

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  DefineJobController ",description = "职务定义接口")
@RestController
@RequestMapping("/define/define_job")
public class DefineJobController extends BaseController {

    @Autowired
    private DefineJobMapper defineJobMapper ;
    @Reference
    private DefineJobService defineJobService ;




    @OperLog(modelName="DefineJobController",action="查询职务信息列表",description = "查询职务信息列表")
    @ApiOperation(value = "查询职务信息列表", notes = "查询职务信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineJobs")
    public MyCommonResult<DefineJobVo> doGetAllDefineJobs(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,
                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineJobService.dealGetDefineJobPages(result,queryFormFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询职务信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineJobController",action="查询职务信息-Dto列表",description = "查询职务信息-Dto列表")
    @ApiOperation(value = "查询职务信息-Dto列表", notes = "查询职务信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineJobDtos")
    public MyCommonResult<DefineJobVo> doGetAllDefineJobDtos(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,
                                                             @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineJobService.dealGetDefineJobDtoPages(result,queryFormFieldBeanList,paginationBean,sortBeans); ;
            dealCommonSuccessCatch(result,"查询职务信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询职务信息", notes = "根据职务id查询职务信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="查询职务信息",description = "根据职务id查询职务信息")
    @PostMapping(value = "/getDefineJobById")
    public MyCommonResult<DefineJobVo> doGetDefineJobById(HttpServletRequest request,String defineJobId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            DefineJob defineJob = defineJobMapper.selectById(defineJobId);
            result.setBean(DefineJobTransfer.transferEntityToVo(defineJob));
            dealCommonSuccessCatch(result,"查询职务信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增职务", notes = "表单方式新增职务", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="新增职务",description = "表单方式新增职务")
    @PostMapping(value = "/doAddDefineJob")
    public MyCommonResult doAddDefineJob(HttpServletRequest request,DefineJobVo defineJobVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                addCount = defineJobService.dealAddDefineJob(defineJobVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新职务信息", notes = "表单方式更新职务信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="更新职务信息",description = "表单方式更新职务信息")
    @PostMapping(value = "/doUpdateDefineJob")
    public MyCommonResult doUpdateDefineJob(HttpServletRequest request,DefineJobVo defineJobVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                changeCount = defineJobService.dealUpdateDefineJob(defineJobVo,loginUser,false) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineJobController",action="批量删除职务",description = "根据职务id批量删除职务")
    @ApiOperation(value = "批量删除职务", notes = "根据职务id批量删除职务", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的职务定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineJobByIds")
    public MyCommonResult doBatchDeleteDefineJobByIds(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                //批量伪删除
                delCount = defineJobService.dealDelDefineJobByArr(delIds,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除职务:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineJobController",action="删除职务",description = "根据职务id删除职务")
    @ApiOperation(value = "删除职务", notes = "根据职务id删除职务", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的职务定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineJobById")
    public MyCommonResult doDelOneDefineJobById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(StringUtils.isNotBlank(delId)){
                delCount = defineJobService.dealDelDefineJob(delId,loginUser);
                dealCommonSuccessCatch(result,"删除职务:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

}
