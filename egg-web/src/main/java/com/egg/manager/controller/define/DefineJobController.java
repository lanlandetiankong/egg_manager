package com.egg.manager.controller.define;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.mapper.define.DefineJobMapper;
import com.egg.manager.service.define.DefineJobService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.define.DefineJobVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
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
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());




    @ApiOperation(value = "查询职务信息列表", notes = "查询职务信息列表", response = String.class)
    @PostMapping(value = "/getAllDefineJobs")
    public MyCommonResult<DefineJobVo> doGetAllDefineJobs(HttpServletRequest request, HttpServletResponse response, String queryObj, String paginationObj) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            defineJobService.dealGetDefineJobPages(result,queryFormFieldBeanList,paginationBean); ;
            dealCommonSuccessCatch(result,"查询职务信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询职务信息", notes = "根据职务id查询职务信息", response = String.class)
    @PostMapping(value = "/getDefineJobById")
    public MyCommonResult<DefineJobVo> doGetDefineJobById(HttpServletRequest request, HttpServletResponse response,String defineJobId) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            DefineJob defineJob = defineJobMapper.selectById(defineJobId);
            result.setBean(DefineJobVo.transferEntityToVo(defineJob));
            dealCommonSuccessCatch(result,"查询职务信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增职务", notes = "表单方式新增职务", response = String.class)
    @PostMapping(value = "/doAddDefineJob")
    public MyCommonResult doAddDefineJob(HttpServletRequest request, HttpServletResponse response, DefineJobVo defineJobVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                addCount = defineJobService.dealAddDefineJob(defineJobVo) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新职务信息", notes = "表单方式更新职务信息", response = String.class)
    @PostMapping(value = "/doUpdateDefineJob")
    public MyCommonResult doUpdateDefineJob(HttpServletRequest request, HttpServletResponse response, DefineJobVo defineJobVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineJobVo == null) {
                throw new Exception("未接收到有效的职务信息！");
            }   else {
                changeCount = defineJobService.dealUpdateDefineJob(defineJobVo,false) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除职务", notes = "根据职务id批量删除职务", response = String.class)
    @PostMapping(value = "/batchDelDefineJobByIds")
    public MyCommonResult doBatchDeleteDefineJobByIds(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                //批量伪删除
                delCount = defineJobService.dealDelDefineJobByArr(delIds);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除职务:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除职务", notes = "根据职务id删除职务", response = String.class)
    @PostMapping(value = "/delOneDefineJobById")
    public MyCommonResult doDelOneDefineJobById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(StringUtils.isNotBlank(delId)){
                delCount = defineJobService.dealDelDefineJob(delId);
                dealCommonSuccessCatch(result,"删除职务:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }





}
