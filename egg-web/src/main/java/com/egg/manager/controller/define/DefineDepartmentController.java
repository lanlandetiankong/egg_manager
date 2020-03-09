package com.egg.manager.controller.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.common.web.tree.CommonTreeSelect;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefineDepartment;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefineDepartmentMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefineDepartmentService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.vo.define.DefineDepartmentVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
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
@Api(value = "API ==>>  DefineDepartmentController ",description = "部门定义接口")
@RestController
@RequestMapping("/define/define_department")
public class DefineDepartmentController extends BaseController{

    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Autowired
    private DefineDepartmentService defineDepartmentService;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @OperLog(modelName="DefineDepartmentController",action="查询部门定义信息列表",description = "查询部门定义信息列表")
    @ApiOperation(value = "查询部门定义信息列表", notes = "查询部门定义信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineDepartments")
    public MyCommonResult<DefineDepartmentVo> doGetAllDefineDepartments(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj,String sortObj) {
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineDepartmentService.dealGetDefineDepartmentPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询部门定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询部门定义信息", notes = "根据部门定义id查询部门定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineDepartmentController",action="查询部门定义信息",description = "根据部门定义id查询部门定义信息")
    @PostMapping(value = "/getDefineDepartmentById")
    public MyCommonResult<DefineDepartmentVo> doGetDefineDepartmentById(HttpServletRequest request, HttpServletResponse response,String defineDepartmentId) {
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            DefineDepartment defineDepartment = defineDepartmentService.selectById(defineDepartmentId);
            result.setBean(DefineDepartmentVo.transferEntityToVo(defineDepartment));
            dealCommonSuccessCatch(result,"查询部门定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefineDepartmentController",action="查询部门TreeSelect",description = "查询部门TreeSelect")
    @ApiOperation(value = "查询部门TreeSelect", notes = "查询部门TreeSelect", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getAllDepartmentTreeSelect")
    public MyCommonResult<DefineDepartment> doGetAllDepartmentTreeSelect() {
        MyCommonResult<DefineDepartment> result = new MyCommonResult<DefineDepartment>() ;
        //筛选与排序
        EntityWrapper<DefineDepartment> defineDepartmentEntityWrapper = new EntityWrapper<DefineDepartment>();
        defineDepartmentEntityWrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        defineDepartmentEntityWrapper.orderBy("level",true);
        defineDepartmentEntityWrapper.orderBy("order_num",true);
        defineDepartmentEntityWrapper.orderBy("create_time",false);
        List<DefineDepartment> allDepartments  = defineDepartmentService.selectList(defineDepartmentEntityWrapper);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(DefineDepartmentConstant.ROOT_DEPARTMENT_ID,allDepartments);
        result.setResultList(treeList);
        return result ;
    }

    @OperLog(modelName="DefineDepartmentController",action="查询被过滤的部门定义TreeSelect",description = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)")
    @ApiOperation(value = "查询被过滤的部门定义TreeSelect", notes = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getDepartmentTreeSelectFilterChildrens")
    public MyCommonResult<DefineDepartment> doGetDepartmentTreeSelectFilterChildrens(String filterId) {
        MyCommonResult<DefineDepartment> result = new MyCommonResult<DefineDepartment>() ;
        List<DefineDepartment> allDepartment  =  defineDepartmentMapper.getDepartmentFilterChildrens(filterId,true);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(DefineDepartmentConstant.ROOT_DEPARTMENT_ID,allDepartment);
        result.setResultList(treeList);
        return result ;
    }
    
    @ApiOperation(value = "新增部门定义", notes = "表单方式新增部门定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineDepartmentController",action="新增部门定义",description = "表单方式新增部门定义")
    @PostMapping(value = "/doAddDefineDepartment")
    public MyCommonResult<DefineDepartmentVo> doAddDefineDepartment(HttpServletRequest request, HttpServletResponse response, DefineDepartmentVo defineDepartmentVo){
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>() ;
        Integer addCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineDepartmentVo == null) {
                throw new Exception("未接收到有效的部门定义！");
            }   else {
                addCount = defineDepartmentService.dealAddDefineDepartment(defineDepartmentVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增部门定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新部门定义", notes = "表单方式更新部门定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineDepartmentController",action="更新部门定义",description = "表单方式更新部门定义")
    @PostMapping(value = "/doUpdateDefineDepartment")
    public MyCommonResult doUpdateDefineDepartment(HttpServletRequest request, HttpServletResponse response, DefineDepartmentVo defineDepartmentVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(defineDepartmentVo == null) {
                throw new Exception("未接收到有效的部门定义！");
            }   else {
                changeCount = defineDepartmentService.dealUpdateDefineDepartment(defineDepartmentVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新部门定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineDepartmentController",action="批量删除部门定义",description = "根据部门id批量删除部门定义")
    @ApiOperation(value = "批量删除部门定义", notes = "根据部门id批量删除部门定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的部门定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineDepartmentByIds")
    public MyCommonResult doBatchDeleteDefineDepartmentById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                delCount = defineDepartmentService.dealDelDefineDepartmentByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除部门定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefineDepartmentController",action="删除部门定义",description = "根据部门id删除部门定义")
    @ApiOperation(value = "删除部门定义", notes = "根据部门id删除部门定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的部门定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineDepartmentById")
    public MyCommonResult doDelOneDefineDepartmentById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineDepartmentService.dealDelDefineDepartment(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除部门定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
