package com.egg.manager.facade.persistence.commons.base.constant.basic;

/**
 * @Description:
 * @ClassName: IErrorKeyConst
 * @Author: zhoucj
 * @Date: 2020/12/10 14:44
 */
public interface IErrorKeyConst {
    String ACTION_FAIL = "error.msg.common.action";
    String EXECUTION_EXCEPTION = "error.msg.common.execution.exception";
    String CREATE_ACTION_FAIL = "error.msg.common.create.action";
    String UPDATE_ACTION_FAIL = "error.msg.common.update.action";
    String NULL_LOGIN_ACCOUNT = "error.msg.common.null.login.account";
    String EMPTY_LOGIN_ACCOUNT = "error.msg.common.empty.login.account";
    String EMPTY_LOGIN_PASSWORD = "error.msg.common.empty.login.password";
    String NOT_MATCH_ACCOUNT_PASSWORD = "error.msg.common.mismatch.account.password";
    String EMPTY_FORM = "error.msg.common.empty.form";
    String UNKNOW_ID = "error.msg.common.unknow.id";
    String UNKNOW_USER_ID = "error.msg.common.unknow.user.id";
    String UNKNOW_TENANT_ID = "error.msg.common.unknow.tenant.id";
    String INVALID_OBJECT = "error.msg.common.invalid.object";
    String EMPTY_OPERATION_OBJECT = "error.msg.common.empty.operation.object";
    String DATA_DOES_NOT_EXIST = "error.msg.common.data.does.not.exist";
    String UNKNOW_ID_COLLECTION = "error.msg.common.unknow.id.collection";
    String EMPTY_COLLECTION = "error.msg.common.empty.collection";
    String USER_IS_LOGIN_OUT = "error.msg.common.user.is.logout";
    String EMPTY_UPLOAD_FILE = "error.msg.common.empty.upload.file";
    String FILE_NOT_EXIST = "error.msg.common.file.not.exist";
    String FILE_NAME_EMPTY = "error.msg.common.file.name.empty";
    String FILE_QUANTITY_EXCEED_LIMIT = "error.msg.common.file.quantity.exceed.limit";
    String EXPORT_FAIL = "error.msg.common.export.fail";
    String UPLOAD_EXCEL_NOT_UPLOADED = "error.msg.common.upload.file.excel.not.uploaded";
    String UPLOAD_EXCEL_TEMPLATE_MISTAKEN = "error.msg.common.upload.file.excel.template.mistaken";
    String SHIRO_UNAUTHORIZED = "error.msg.shiro.unauthorized";
    String NO_OPERATION_AUTHORITY = "error.msg.shiro.no.operation.authority";
    String BUSINESS_EXCEPTION = "error.msg.business.exception";
    String FORM_INCORRECT_PARAM = "error.msg.form.incorrect.param";
    String UNKNOW_METHOD_OPERATION_NAME = "error.msg.unknow.method.operation.name";
    String ACTIVATED_ITEMS_CANNOT_BE_DELETED = "error.msg.activated.items.cannot.be.deleted";
    String PARAM_CANNOT_EMPTY = "error.msg.param.cannot.empty";
    String NOT_ALLOW_DELETE_ALL_DATA = "error.msg.not.allowed.delete.all.data";
    String UPDATE_QUANTITY_DOES_NOT_MATCH = "error.msg.update.quantity.does.not.match";
    String DELETE_QUANTITY_DOES_NOT_MATCH = "error.msg.delete.quantity.does.not.match";
    String REFLEX_CLS_NOT_FOUND = "error.msg.reflex.cls.not.found";
    String JWT_TOKEN_INVALID = "error.msg.jwt.token.invalid";
    String JWT_TOKEN_MISMATCH = "error.msg.jwt.token.mismatch";
}
