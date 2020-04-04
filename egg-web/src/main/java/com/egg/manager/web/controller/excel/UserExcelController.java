package com.egg.manager.web.controller.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.entity.user.UserJob;
import com.egg.manager.persistence.mapper.user.UserJobMapper;
import com.egg.manager.persistence.vo.user.UserJobVo;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.service.user.UserJobService;
import com.egg.manager.web.controller.BaseController;
import com.github.crab2died.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "API ==>>  UserExcelController ",description = "用户Excel处理接口")
@RestController
@RequestMapping("/excel/user_excel")
public class UserExcelController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());






    @PostMapping(value = "/upload_excel")
    public MyCommonResult<UserJobVo> doUploadExcel(HttpServletRequest request) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>() ;
        try{
            String path = "/excel/Test1.xlsx" ;
            dealCommonSuccessCatch(result,"上传excel:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }








}
