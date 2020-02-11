package com.egg.manager.controller.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserJob;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.mapper.user.UserJobMapper;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserJobService;
import com.egg.manager.vo.user.UserJobVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/user/user_job")
public class UserJobController extends BaseController{


    @Autowired
    private UserJobMapper userJobMapper ;
    @Autowired
    private UserJobService userJobService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());






    @ApiOperation(value = "查询用户职务列表", notes = "查询用户职务列表", response = String.class)
    @PostMapping(value = "/getAllUserJobs")
    public MyCommonResult<UserJobVo> doGetAllUserAccouts(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            userJobService.dealGetUserJobPages(result,queryFormFieldBeanList,paginationBean);
            dealCommonSuccessCatch(result,"查询用户职务信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户职务信息", notes = "根据用户职务id查询用户职务信息", response = String.class)
    @PostMapping(value = "/getUserJobById")
    public MyCommonResult<UserJobVo> doGetUserJobById(HttpServletRequest request, HttpServletResponse response,String jobId) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>() ;
        try{
            UserJob vo = userJobMapper.selectById(jobId);
            result.setBean(UserJobVo.transferEntityToVo(vo));
            dealCommonSuccessCatch(result,"查询用户职务信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }






}
