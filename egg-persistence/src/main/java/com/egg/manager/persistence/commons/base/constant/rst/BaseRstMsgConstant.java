package com.egg.manager.persistence.commons.base.constant.rst;

/**
 * @author zhoucj
 * @description: 控制器操作信息-基本常量
 * @date 2020/10/21
 */
public class BaseRstMsgConstant {
    public final static String ACTION_SUCCESS_MSG = "操作成功!";
    public final static String ACTION_FAIL_MSG = "操作失败!";
    private final static String NULL_LOGIN_ACCOUNT = "账号不存在!";
    private final static String EMPTY_LOGIN_ACCOUNT = "账号不能为空!";
    private final static String EMPTY_LOGIN_PASSWORD = "密码不能为空!";
    private final static String NOT_MATCH_ACCOUNT_PASSWORD = "账号密码不匹配!";
    private final static String EMPTY_FORM = "提交的form为空!";
    private final static String UNKNOW_ID = "未知id!";
    private final static String UNKNOW_ID_COLLECTION = "未知id集合!";
    private final static String USER_IS_LOGIN_OUT = "用户未登录!";
    private final static String EMPTY_UPLOAD_FILE = "上传的文件为空!";


    /**
     * 错误信息
     */
    public static class ErrorMsg {
        public static String nullLoginAccount() {
            return NULL_LOGIN_ACCOUNT + ACTION_FAIL_MSG;
        }

        public static String emptyLoginAccount() {
            return EMPTY_LOGIN_ACCOUNT + ACTION_FAIL_MSG;
        }

        public static String emptyLoginPassword() {
            return EMPTY_LOGIN_PASSWORD + ACTION_FAIL_MSG;
        }

        public static String emptyForm() {
            return EMPTY_FORM + ACTION_FAIL_MSG;
        }

        public static String unknowId() {
            return UNKNOW_ID + ACTION_FAIL_MSG;
        }

        public static String unknowIdCollection() {
            return UNKNOW_ID_COLLECTION + ACTION_FAIL_MSG;
        }

        public static String userIsLoingOut() {
            return USER_IS_LOGIN_OUT + ACTION_FAIL_MSG;
        }

        public static String emptyUploadFile() {
            return EMPTY_UPLOAD_FILE + ACTION_FAIL_MSG;
        }

        public static String notMatchaccountPassword() {
            return NOT_MATCH_ACCOUNT_PASSWORD + ACTION_FAIL_MSG;
        }
    }


    public static class SuccessMsg {

    }


}
