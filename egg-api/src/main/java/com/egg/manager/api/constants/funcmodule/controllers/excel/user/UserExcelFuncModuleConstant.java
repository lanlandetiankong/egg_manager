package com.egg.manager.api.constants.funcmodule.controllers.excel.user;

import com.egg.manager.api.constants.funcmodule.EggBaseFuncModuleNameConstant;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public class UserExcelFuncModuleConstant extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = userExcel;

    public static class Success {
        private final static boolean DEFAULT_OK_FLAG = true;
        public static String EXCEL_EXPORT_CHECK = doExcelExportCheck(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String EXCEL_EXPORT_ALL = doExcelExportAll(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String EXCEL_IMPORT_DATA = doExcelImportData(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }

    public static class Failure {
        private final static boolean DEFAULT_OK_FLAG = false;
        public static String EXCEL_EXPORT_CHECK = doExcelExportCheck(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String EXCEL_EXPORT_ALL = doExcelExportAll(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
        public static String EXCEL_IMPORT_DATA = doExcelImportData(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }

}
