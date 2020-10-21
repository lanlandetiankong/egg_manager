package com.egg.manager.api.constants.funcmodule;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhoucj
 * @description:按功能模块取得
 * @date 2020/10/21
 */
public class EggBaseFuncModuleNameConstant {

    protected final static String announcement = "通知公告";
    protected final static String announcementDraft = "通知公告草稿";
    protected final static String announcementTag = "通知公告标签";


    protected final static String defineDepartment = "部门定义";
    protected final static String defineGroup = "组别定义";
    protected final static String defineJob = "职务定义";
    protected final static String defineMenu = "菜单定义";
    protected final static String definePermission = "权限定义";
    protected final static String defineRole = "角色定义";
    protected final static String userExcel = "用户Excel";
    protected final static String smartFormDefinition = "表单定义";
    protected final static String smartFormTypeDefinition = "表单类型定义";
    protected final static String userLogin = "用户登录";
    protected final static String emailSendRecord = "邮件发送记录";
    protected final static String defineModule = "模块定义";
    protected final static String defineTenant = "租户定义";
    protected final static String roleMenu = "角色<->菜单关联";
    protected final static String rolePermission = "角色<->权限关联";
    protected final static String userAccount = "用户账号";
    protected final static String userDepartment = "用户<->部门关联";
    protected final static String userGroup = "用户<->组别关联";
    protected final static String userJob = "用户<->职务关联";
    protected final static String userRole = "用户<->角色关联";
    protected final static String userTenant = "用户<->租户关联";


    protected final static String UPLOAD_EXCEL = "excel上传";
    protected final static String UPLOAD_IMG = "图片上传";
    protected final static String userAccountCommonComp = "用户账号(通用组件)";

    protected final static String commonBinding = "通用<接口>";
    protected final static String moduleCommon = "通用接口<模块>";
    protected final static String permissionCommon = "通用接口<权限>";
    protected final static String userCommon = "通用接口<用户>";


    protected final static String controllerAdviser = "接口异常";


    protected static final String doLoginOper(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.LOGIN_OPER + funcModuleName + EggFuncActionNameConstant.Suffix.LOGIN_OPER;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryPage(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_PAGE + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_PAGE;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryList(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_LIST + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_LIST;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryEnumList(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_ONE_BY_ID + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_ONE_BY_ID;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryOneById(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_ENUM_LIST + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_ENUM_LIST;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryTreeSelect(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_TREE_SELECT + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_TREE_SELECT;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doQueryGranted(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.QUERY_GRANTED + funcModuleName + EggFuncActionNameConstant.Suffix.QUERY_GRANTED;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }


    protected static final String doCreate(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.CREATE_OPER + funcModuleName + EggFuncActionNameConstant.Suffix.CREATE_OPER;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doUpdate(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.UPDATE_OPER + funcModuleName + EggFuncActionNameConstant.Suffix.UPDATE_OPER;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doUpdateState(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.UPDATE_STATE + funcModuleName + EggFuncActionNameConstant.Suffix.UPDATE_STATE;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doGrantOper(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.GRANT_OPER + funcModuleName + EggFuncActionNameConstant.Suffix.GRANT_OPER;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doSettingOper(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.SETTING_OPER + funcModuleName + EggFuncActionNameConstant.Suffix.SETTING_OPER;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doDeleteById(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.DELETE_BY_ID + funcModuleName + EggFuncActionNameConstant.Suffix.DELETE_BY_ID;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doBatchDeleteByIds(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.BATCH_DELETE_BY_IDS + funcModuleName + EggFuncActionNameConstant.Suffix.BATCH_DELETE_BY_IDS;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doBatchEnsure(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.BATCH_ENSURE + funcModuleName + EggFuncActionNameConstant.Suffix.BATCH_ENSURE;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doPublish(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.PUBLISH + funcModuleName + EggFuncActionNameConstant.Suffix.PUBLISH;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doBatchPublish(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.BATCH_PUBLISH + funcModuleName + EggFuncActionNameConstant.Suffix.BATCH_PUBLISH;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doUploadExcel(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.UPLOAD_EXCEL + funcModuleName + EggFuncActionNameConstant.Suffix.UPLOAD_EXCEL;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doExcelExportCheck(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.EXCEL_EXPORT_CHECK + funcModuleName + EggFuncActionNameConstant.Suffix.EXCEL_EXPORT_CHECK;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doExcelExportAll(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.EXCEL_EXPORT_ALL + funcModuleName + EggFuncActionNameConstant.Suffix.EXCEL_EXPORT_ALL;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doExcelImportData(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.EXCEL_IMPORT_DATA + funcModuleName + EggFuncActionNameConstant.Suffix.EXCEL_IMPORT_DATA;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }

    protected static final String doUploadImg(String funcModuleName, Boolean isSuccess) {
        funcModuleName = StringUtils.defaultIfBlank(funcModuleName, "");
        String msg = EggFuncActionNameConstant.Prefix.UPLOAD_IMG + funcModuleName + EggFuncActionNameConstant.Suffix.UPLOAD_IMG;
        if (isSuccess == null) {
            return msg;
        } else {
            msg += (isSuccess == true) ? BaseRstMsgConstant.ACTION_SUCCESS_MSG : BaseRstMsgConstant.ACTION_FAIL_MSG;
            return msg;
        }
    }


}
