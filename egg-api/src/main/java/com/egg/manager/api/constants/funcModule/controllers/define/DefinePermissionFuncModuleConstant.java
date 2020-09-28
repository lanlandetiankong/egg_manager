package com.egg.manager.api.constants.funcModule.controllers.define;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class DefinePermissionFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = definePermission;


    public static class Success {
        private final static boolean defaultFlag = true;
        public static String loginOper = doLoginOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryPage = doQueryPage(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryList = doQueryList(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryOneById = doQueryOneById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryEnumList = doQueryOneById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryTreeSelect = doQueryTreeSelect(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryGranted = doQueryGranted(KEY_FUNC_MODULE_NAME,defaultFlag);

        public static String batchPublish = doBatchPublish(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String publish = doPublish(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String create = doCreate(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String update = doUpdate(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String updateState = doUpdateState(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String grantOper = doGrantOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String settingOper = doSettingOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String deleteById = doDeleteById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String batchEnsure = doBatchEnsure(KEY_FUNC_MODULE_NAME,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String loginOper = doLoginOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryPage = doQueryPage(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryList = doQueryList(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryOneById = doQueryOneById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryEnumList = doQueryOneById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryTreeSelect = doQueryTreeSelect(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String queryGranted = doQueryGranted(KEY_FUNC_MODULE_NAME,defaultFlag);

        public static String batchPublish = doBatchPublish(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String publish = doPublish(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String create = doCreate(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String update = doUpdate(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String updateState = doUpdateState(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String grantOper = doGrantOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String settingOper = doSettingOper(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String deleteById = doDeleteById(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String batchEnsure = doBatchEnsure(KEY_FUNC_MODULE_NAME,defaultFlag);
    }
}
