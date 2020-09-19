package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.user.UserJobService;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.db.mysql.mapper.user.UserJobMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserJobTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserJobVo;
import com.egg.manager.web.controller.BaseController;
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
@Api(value = "API ==>>  UserJobController ",description = "用户职务接口")
@RestController
@RequestMapping("/user/user_job")
public class UserJobController extends BaseController{

    @Autowired
    private UserJobMapper userJobMapper ;
    @Reference
    private UserJobService userJobService ;


    @PcWebQueryLog(action="查询用户职务列表",description = "查询用户职务列表",fullPath = "/user/user_job/getAllUserJobs")
    @ApiOperation(value = "查询用户职务列表", notes = "查询用户职务列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllUserJobs")
    public MyCommonResult<UserJobVo> doGetAllUserAccouts(HttpServletRequest request, String queryObj, String paginationObj, String sortObj
                    ,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = userJobService.dealGetUserJobPages(loginUser,result,queryFormFieldBeanList,paginationBean,sortBeans);
            dealCommonSuccessCatch(result,"查询用户职务信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户职务信息", notes = "根据用户职务id查询用户职务信息", response = MyCommonResult.class,httpMethod = "POST")
    @PcWebQueryLog(action="查询用户职务信息",description = "根据用户职务id查询用户职务信息",fullPath = "/user/user_job/getUserJobById")
    @PostMapping(value = "/getUserJobById")
    public MyCommonResult<UserJobVo> doGetUserJobById(HttpServletRequest request, String jobId) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>() ;
        try{
            UserJob vo = userJobMapper.selectById(jobId);
            result.setBean(UserJobTransfer.transferEntityToVo(vo));
            dealCommonSuccessCatch(result,"查询用户职务信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }






}
