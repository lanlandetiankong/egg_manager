package com.egg.manager.api.constants.funcmodule.controllers.common.binding;

import com.egg.manager.api.constants.funcmodule.EggBaseFuncModuleNameConstant;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public class UserCommonFuncModuleConstant extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = userCommon;

    public static class Success {
        private final static boolean DEFAULT_OK_FLAG = true;
        public static String LOGIN_OPER = doLoginOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_PAGE = doQueryPage(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_LIST = doQueryList(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_ONE_BY_ID = doQueryOneById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_ENUM_LIST = doQueryOneById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_TREE_SELECT = doQueryTreeSelect(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_GRANTED = doQueryGranted(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);

        public static String BATCH_PUBLISH = doBatchPublish(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String PUBLISH = doPublish(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String CREATE_OPER = doCreate(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String UPDATE_OPER = doUpdate(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String UPDATE_STATE = doUpdateState(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String GRANT_OPER = doGrantOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String SETTING_OPER = doSettingOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String DELETE_BY_ID = doDeleteById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String BATCH_DELETE_BY_IDS = doBatchDeleteByIds(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String BATCH_ENSURE = doBatchEnsure(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }

    public static class Failure {
        private final static boolean DEFAULT_OK_FLAG = false;
        public static String LOGIN_OPER = doLoginOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_PAGE = doQueryPage(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_LIST = doQueryList(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_ONE_BY_ID = doQueryOneById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_ENUM_LIST = doQueryOneById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_TREE_SELECT = doQueryTreeSelect(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String QUERY_GRANTED = doQueryGranted(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);

        public static String BATCH_PUBLISH = doBatchPublish(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String PUBLISH = doPublish(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String CREATE_OPER = doCreate(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String UPDATE_OPER = doUpdate(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String UPDATE_STATE = doUpdateState(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String GRANT_OPER = doGrantOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String SETTING_OPER = doSettingOper(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String DELETE_BY_ID = doDeleteById(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String BATCH_DELETE_BY_IDS = doBatchDeleteByIds(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String BATCH_ENSURE = doBatchEnsure(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }
}
