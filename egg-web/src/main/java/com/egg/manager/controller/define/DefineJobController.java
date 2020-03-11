package com.egg.manager.controller.define;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefineJobMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefineJobService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.define.DefineJobVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Api(value = "API ==>>  DefineJobController ",description = "职务定义接口")
@RestController
@RequestMapping("/define/define_job")
public class DefineJobController extends BaseController {

    @Autowired
    private DefineJobMapper defineJobMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private DefineJobService defineJobService ;
    @Autowired
    private CommonFuncService commonFuncService ;


    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());




    @OperLog(modelName="DefineJobController",action="查询职务信息列表",description = "查询职务信息列表")
    @ApiOperation(value = "查询职务信息列表", notes = "查询职务信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineJobs")
    public MyCommonResult<DefineJobVo> doGetAllDefineJobs(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj,String sortObj) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
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
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询职务信息", notes = "根据职务id查询职务信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="查询职务信息",description = "根据职务id查询职务信息")
    @PostMapping(value = "/getDefineJobById")
    public MyCommonResult<DefineJobVo> doGetDefineJobById(HttpServletRequest request, HttpServletResponse response,String defineJobId) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefineJob defineJob = defineJobMapper.selectById(defineJobId);
            result.setBean(DefineJobVo.transferEntityToVo(defineJob));
            dealCommonSuccessCatch(result,"查询职务信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增职务", notes = "表单方式新增职务", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="新增职务",description = "表单方式新增职务")
    @PostMapping(value = "/doAddDefineJob")
    public MyCommonResult doAddDefineJob(HttpServletRequest request, HttpServletResponse response, DefineJobVo defineJobVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                addCount = defineJobService.dealAddDefineJob(defineJobVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新职务信息", notes = "表单方式更新职务信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineJobController",action="更新职务信息",description = "表单方式更新职务信息")
    @PostMapping(value = "/doUpdateDefineJob")
    public MyCommonResult doUpdateDefineJob(HttpServletRequest request, HttpServletResponse response, DefineJobVo defineJobVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                changeCount = defineJobService.dealUpdateDefineJob(defineJobVo,loginUser,false) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineJobController",action="批量删除职务",description = "根据职务id批量删除职务")
    @ApiOperation(value = "批量删除职务", notes = "根据职务id批量删除职务", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的职务定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineJobByIds")
    public MyCommonResult doBatchDeleteDefineJobByIds(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                //批量伪删除
                delCount = defineJobService.dealDelDefineJobByArr(delIds,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除职务:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineJobController",action="删除职务",description = "根据职务id删除职务")
    @ApiOperation(value = "删除职务", notes = "根据职务id删除职务", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的职务定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineJobById")
    public MyCommonResult doDelOneDefineJobById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                delCount = defineJobService.dealDelDefineJob(delId,loginUser);
                dealCommonSuccessCatch(result,"删除职务:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }





}
