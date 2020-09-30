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
        private final static boolean DEFAULT_OK_FLAG = true;
        public static String excelExportCheck = doExcelExportCheck(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
        public static String excelExportAll = doExcelExportAll(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
        public static String excelImportData = doExcelImportData(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
    }

    public static class Failure {
        private final static boolean DEFAULT_OK_FLAG = false;
        public static String excelExportCheck = doExcelExportCheck(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
        public static String excelExportAll = doExcelExportAll(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
        public static String excelImportData = doExcelImportData(KEY_FUNC_MODULE_NAME,DEFAULT_OK_FLAG);
    }

}
