package com.egg.manager.api.constants.funcModule;

import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 按功能模块取得
 * @ClassName: EggBaseFuncModuleNameConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 11:54
 */
public class EggBaseFuncModuleNameConstant {

    protected final static String announcement = "通知公告" ;
    protected final static String announcementDraft = "通知公告草稿" ;
    protected final static String announcementTag = "通知公告标签" ;


    protected final static String defineDepartment = "部门定义" ;
    protected final static String defineGroup = "组别定义" ;
    protected final static String defineJob = "职务定义" ;
    protected final static String defineMenu = "菜单定义" ;
    protected final static String definePermission = "权限定义" ;
    protected final static String defineRole = "角色定义" ;
    protected final static String userExcel = "用户Excel" ;
    protected final static String smartFormDefinition = "表单定义" ;
    protected final static String smartFormTypeDefinition = "表单类型定义" ;
    protected final static String userLogin = "用户登录" ;
    protected final static String emailSendRecord = "邮件发送记录" ;
    protected final static String defineModule = "模块定义" ;
    protected final static String defineTenant = "租户定义" ;
    protected final static String roleMenu = "角色<->菜单关联" ;
    protected final static String rolePermission = "角色<->权限关联" ;
    protected final static String userAccount = "用户账号" ;
    protected final static String userDepartment = "用户<->部门关联" ;
    protected final static String userGroup = "用户<->组别关联" ;
    protected final static String userJob = "用户<->职务关联" ;
    protected final static String userRole = "用户<->角色关联" ;
    protected final static String userTenant = "用户<->租户关联" ;





    protected static final String doCreate(String funcModuleName,Boolean isSuccess)  {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName,"");
        String msg = EggFuncActionNameConstant.Prefix.create + funcModuleName + EggFuncActionNameConstant.Suffix.create;
        if(isSuccess == null){
            return msg ;
        }   else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.actionSuccessMsg : BaseRstMsgConstant.actionFailMsg ;
            return msg ;
        }
    }

    protected static final String doUpdate(String funcModuleName,Boolean isSuccess)  {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName,"");
        String msg =  EggFuncActionNameConstant.Prefix.update + funcModuleName + EggFuncActionNameConstant.Suffix.update;
        if(isSuccess == null){
            return msg ;
        }   else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.actionSuccessMsg : BaseRstMsgConstant.actionFailMsg ;
            return msg ;
        }
    }

    protected static final String doDeleteById(String funcModuleName,Boolean isSuccess)  {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName,"");
        String msg =  EggFuncActionNameConstant.Prefix.deleteById + funcModuleName + EggFuncActionNameConstant.Suffix.deleteById;
        if(isSuccess == null){
            return msg ;
        }   else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.actionSuccessMsg : BaseRstMsgConstant.actionFailMsg ;
            return msg ;
        }
    }

    protected static final String doBatchDeleteByIds(String funcModuleName,Boolean isSuccess)  {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName,"");
        String msg = EggFuncActionNameConstant.Prefix.batchDeleteByIds + funcModuleName + EggFuncActionNameConstant.Suffix.batchDeleteByIds;
        if(isSuccess == null){
            return msg ;
        }   else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.actionSuccessMsg : BaseRstMsgConstant.actionFailMsg ;
            return msg ;
        }
    }



}
