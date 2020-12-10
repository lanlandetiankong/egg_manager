package com.egg.manager.persistence.commons.base.constant.basic;

import com.egg.manager.persistence.commons.util.i18n.I18nUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhoucj
 * @description 控制器操作信息-基本常量
 * @date 2020/10/21
 */
public class BaseRstMsgConstant implements ISuccessKeyConst, IErrorKeyConst, IWarningKeyConst, IInfoKeyConst {

    /**
     * 错误信息
     */
    public static class ErrorMsg {
        public static String actionFail(){
            return I18nUtil.get(ACTION_FAIL);
        }
        public static String executionException(String suffix){
            return I18nUtil.get(EXECUTION_EXCEPTION)+StringUtils.defaultIfBlank(suffix,"");
        }
        public static String createActionFail(){
            return I18nUtil.get(CREATE_ACTION_FAIL);
        }
        public static String updateActionFail(){
            return I18nUtil.get(UPDATE_ACTION_FAIL);
        }
        public static String nullLoginAccount() {
            return I18nUtil.get(NULL_LOGIN_ACCOUNT) + actionFail();
        }

        public static String emptyLoginAccount() {
            return I18nUtil.get(EMPTY_LOGIN_ACCOUNT) + actionFail();
        }

        public static String emptyLoginPassword() {
            return I18nUtil.get(EMPTY_LOGIN_PASSWORD) + actionFail();
        }

        public static String emptyForm() {
            return I18nUtil.get(EMPTY_FORM) + actionFail();
        }

        public static String unknowId() {
            return I18nUtil.get(UNKNOW_ID) + actionFail();
        }

        public static String unknowUserId() {
            return I18nUtil.get(UNKNOW_USER_ID) + actionFail();
        }

        public static String unknowTenantId() {
            return I18nUtil.get(UNKNOW_TENANT_ID) + actionFail();
        }

        public static String invalidObject() {
            return I18nUtil.get(INVALID_OBJECT) + actionFail();
        }
        public static String emptyOperationObject() {
            return I18nUtil.get(EMPTY_OPERATION_OBJECT) + actionFail();
        }
        public static String dataDoesNotExist() {
            return I18nUtil.get(DATA_DOES_NOT_EXIST) + actionFail();
        }

        public static String unknowIdCollection() {
            return I18nUtil.get(UNKNOW_ID_COLLECTION) + actionFail();
        }
        public static String emptyCollection() {
            return I18nUtil.get(EMPTY_COLLECTION) + actionFail();
        }

        public static String userIsLoingOut() {
            return I18nUtil.get(USER_IS_LOGIN_OUT) + actionFail();
        }

        public static String emptyUploadFile() {
            return I18nUtil.get(EMPTY_UPLOAD_FILE) + actionFail();
        }
        public static String uploadExcelNotUploaded() {
            return I18nUtil.get(UPLOAD_EXCEL_NOT_UPLOADED) + actionFail();
        }
        public static String uploadExcelTemplateMistaken() {
            return I18nUtil.get(UPLOAD_EXCEL_TEMPLATE_MISTAKEN) + actionFail();
        }

        public static String notMatchaccountPassword() {
            return I18nUtil.get(NOT_MATCH_ACCOUNT_PASSWORD) + actionFail();
        }
        public static String unauthorized() {
            return I18nUtil.get(UNAUTHORIZED) + actionFail();
        }
        public static String noOperationAuthority() {
            return I18nUtil.get(NO_OPERATION_AUTHORITY) + actionFail();
        }
        public static String businessException() {
            return I18nUtil.get(BUSINESS_EXCEPTION) + actionFail();
        }
        public static String formIncorrectParam() {
            return I18nUtil.get(FORM_INCORRECT_PARAM) + actionFail();
        }
        public static String unknowMethodOperationName() {
            return I18nUtil.get(UNKNOW_METHOD_OPERATION_NAME) + actionFail();
        }
        public static String activatedItemsCannotBeDeleted() {
            return I18nUtil.get(ACTIVATED_ITEMS_CANNOT_BE_DELETED) + actionFail();
        }
        public static String paramCannotEmpty(String arg1) {
            return I18nUtil.get(PARAM_CANNOT_EMPTY,arg1) + actionFail();
        }
        public static String notAllowDeleteAllData() {
            return I18nUtil.get(NOT_ALLOW_DELETE_ALL_DATA) + actionFail();
        }
        public static String updateQuantityDoesNotMatch(Long arg1,Long arg2) {
            return I18nUtil.get(UPDATE_QUANTITY_DOES_NOT_MATCH,arg1,arg2) + actionFail();
        }
        public static String deleteQuantityDoesNotMatch(Long arg1,Long arg2) {
            return I18nUtil.get(DELETE_QUANTITY_DOES_NOT_MATCH,arg1,arg2) + actionFail();
        }
    }


    public static class SuccessMsg {
        public static String actionSuccess(){
            return I18nUtil.get(ACTION_SUCCESS);
        }
    }


    public static class InfoMsg {
        public static String excelExportData(String prefix){
            return StringUtils.defaultIfBlank(prefix,"")+I18nUtil.get(EXCEL_EXPORT_DATA);
        }
    }

    public static class WarningMsg {
        public static String selectAtLeastOneExportData(){
            return I18nUtil.get(SELECT_AT_LEAST_ONE_EXPORT_DATA);
        }
    }

}
