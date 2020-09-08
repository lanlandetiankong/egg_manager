package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.user.UserDepartmentService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.db.mysql.mapper.user.UserDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;
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
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  UserDepartmentController ",description = "用户与部门关联表的接口")
@RestController
@RequestMapping("/user/user_department")
public class UserDepartmentController extends BaseController{


    @Autowired
    private UserDepartmentMapper userDepartmentMapper ;
    @Reference
    private UserDepartmentService userDepartmentService ;



    @PcWebQueryLog(action="查询 [用户与部门关联] 列表",description = "查询 [用户与部门关联] 列表",fullPath = "/user/user_department/getAllUserDepartments")
    @ApiOperation(value = "查询 [用户与部门关联] 列表", notes = "查询 [用户与部门关联] 列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllUserDepartments")
    public MyCommonResult<UserDepartmentVo> doGetAllUserDepartments(HttpServletRequest request,
                                                                    String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserDepartmentVo> result = new MyCommonResult<UserDepartmentVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = userDepartmentService.dealGetUserDepartmentPages(result,queryFormFieldBeanList,paginationBean,sortBeans);
            dealCommonSuccessCatch(result,"查询 [用户与部门关联] 信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询 [用户与部门关联] 信息", notes = "根据 [用户与部门关联] id查询 [用户与部门关联] 信息", response = MyCommonResult.class,httpMethod = "POST")
    @PcWebQueryLog(action="查询 [用户与部门关联] 信息",description = "根据 [用户与部门关联] id查询 [用户与部门关联] 信息",fullPath = "/user/user_department/getUserDepartmentById")
    @PostMapping(value = "/getUserDepartmentById")
    public MyCommonResult<UserDepartmentVo> doGetUserDepartmentById(HttpServletRequest request, String departmentId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserDepartmentVo> result = new MyCommonResult<UserDepartmentVo>() ;
        try{
            UserDepartment vo = userDepartmentMapper.selectById(departmentId);
            result.setBean(UserDepartmentTransfer.transferEntityToVo(vo));
            dealCommonSuccessCatch(result,"查询 [用户与部门关联] 信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增 [用户与部门关联] ", notes = "表单方式新增 [用户与部门关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @PcWebOperationLog(action="新增 [用户与部门关联] ",description = "表单方式新增 [用户与部门关联] ",fullPath = "/user/user_department/doAddUserDepartment")
    @PostMapping(value = "/doAddUserDepartment")
    public MyCommonResult doAddUserDepartment(HttpServletRequest request, UserDepartmentVo userDepartmentVo, @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            if(userDepartmentVo == null) {
                throw new Exception("未接收到有效的 [用户与部门关联] 信息！");
            }   else {
                addCount = userDepartmentService.dealAddUserDepartment(userDepartmentVo,loginUser);
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增 [用户与部门关联] :"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    

    @PcWebOperationLog(action="批量删除 [用户与部门关联] ",description = "根据 [用户与部门关联] id批量删除 [用户与部门关联] ",fullPath = "/user/user_department/batchDelUserDepartmentByIds")
    @ApiOperation(value = "批量删除 [用户与部门关联] ", notes = "根据 [用户与部门关联] id批量删除 [用户与部门关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的 [用户与部门关联] id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelUserDepartmentByIds")
    public MyCommonResult doBatchDeleteUserDepartmentById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = userDepartmentService.dealDelUserDepartmentByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除 [用户与部门关联] :"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @PcWebOperationLog(action="删除 [用户与部门关联] ",description = "根据 [用户与部门关联] id删除 [用户与部门关联] ",fullPath = "/user/user_department/delOneUserDepartmentByIds")
    @ApiOperation(value = "删除 [用户与部门关联] ", notes = "根据 [用户与部门关联] id删除 [用户与部门关联] ", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的 [用户与部门关联] id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneUserDepartmentByIds")
    public MyCommonResult doDelOneUserDepartmentById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(StringUtils.isNotBlank(delId)){
                delCount = userDepartmentService.dealDelUserDepartment(delId,loginUser);
                dealCommonSuccessCatch(result,"删除 [用户与部门关联] :"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


}
