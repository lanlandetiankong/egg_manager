package com.egg.manager.api.constants.controllers;

/**
 * @Description: 控制器基本 常量
 * @ClassName: BaseRstMsgConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 9:23
 */
public class BaseRstMsgConstant {
    private final static String actionSuccessMsg = "操作成功!";
    private final static String actionFailMsg = "操作失败!";
    private final static String nullLoginAccount = "账号不存在!";
    private final static String emptyLoginAccount = "账号不能为空!";
    private final static String emptyLoginPassword = "密码不能为空!";
    private final static String notMatchaccountPassword = "账号密码不匹配!";
    private final static String emptyForm = "提交的form为空!";
    private final static String unknowId = "未知id!";
    private final static String unknowIdCollection = "未知id集合!";
    private final static String userIsLoingOut = "用户未登录!";
    private final static String emptyUploadFile = "上传的文件为空!";


    /**
     * 错误信息
     */
    public static class ErrorMsg {
        public static String nullLoginAccount(){
            return nullLoginAccount + actionFailMsg;
        }

        public static String emptyLoginAccount(){
            return emptyLoginAccount + actionFailMsg;
        }

        public static String emptyLoginPassword(){
            return emptyLoginPassword + actionFailMsg;
        }

        public static String emptyForm(){
            return emptyForm + actionFailMsg;
        }
        public static String unknowId(){
            return unknowId + actionFailMsg;
        }
        public static String unknowIdCollection(){
            return unknowIdCollection + actionFailMsg;
        }
        public static String userIsLoingOut(){
            return userIsLoingOut + actionFailMsg;
        }
        public static String emptyUploadFile(){
            return emptyUploadFile + actionFailMsg;
        }
        public static String notMatchaccountPassword(){
            return notMatchaccountPassword + actionFailMsg;
        }


    }



}
