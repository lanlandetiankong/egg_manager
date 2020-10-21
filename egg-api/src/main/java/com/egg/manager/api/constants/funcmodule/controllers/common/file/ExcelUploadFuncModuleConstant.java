package com.egg.manager.api.constants.funcmodule.controllers.common.file;

import com.egg.manager.api.constants.funcmodule.EggBaseFuncModuleNameConstant;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public class ExcelUploadFuncModuleConstant extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = UPLOAD_IMG;

    public static class Success {
        private final static boolean DEFAULT_OK_FLAG = true;
        public static String UPLOAD_EXCEL = doUploadExcel(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }

    public static class Failure {
        private final static boolean DEFAULT_OK_FLAG = false;
        public static String UPLOAD_EXCEL = doUploadExcel(KEY_FUNC_MODULE_NAME, DEFAULT_OK_FLAG);
    }

}
