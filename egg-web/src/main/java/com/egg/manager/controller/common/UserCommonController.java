package com.egg.manager.controller.common;

import com.egg.manager.common.base.beans.FrontSelectBean;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserAccount;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 19:08
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/common_api/user")
public class UserCommonController extends BaseController{


    @ApiOperation(value = "检索所有用户类型", notes = "检索所有用户类型", response = String.class)
    @PostMapping(value = "/getAllUserTypeEnumList")
    public MyCommonResult doGetAllUserTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccountBaseTypeEnum[] enums = UserAccountBaseTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if(enums != null && enums.length > 0){
                for (UserAccountBaseTypeEnum enumObj : enums){
                    beanList.add(new FrontSelectBean(enumObj.getValue(),enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "检索所有用户锁定状态", notes = "检索所有用户锁定状态", response = String.class)
    @PostMapping(value = "/getAllUserLockStateEnumList")
    public MyCommonResult doGetAllUserLockStateEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = new MyCommonResult() ;
        try{
            List<FrontSelectBean> beanList = new ArrayList<>();
            beanList.add(new FrontSelectBean(0,"未锁定"));
            beanList.add(new FrontSelectBean(1,"已锁定"));
            result.setEnumList(beanList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }
}
