package com.egg.manager.api.constants.funcmodule.controllers.excel.user;

import com.egg.manager.api.constants.funcmodule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class UserExcelFuncModuleConstant extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = userExcel;

    public static class Success {
        private final static boolean defaultFlag = true;
        public static String excelExportCheck = doExcelExportCheck(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String excelExportAll = doExcelExportAll(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String excelImportData = doExcelImportData(KEY_FUNC_MODULE_NAME,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String excelExportCheck = doExcelExportCheck(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String excelExportAll = doExcelExportAll(KEY_FUNC_MODULE_NAME,defaultFlag);
        public static String excelImportData = doExcelImportData(KEY_FUNC_MODULE_NAME,defaultFlag);
    }

}
