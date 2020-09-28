package com.egg.manager.api.constants.funcModule.controllers.common.file;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class ImgUploadFuncModuleConstant extends EggBaseFuncModuleNameConstant {
    protected static final String KEY_FUNC_MODULE_NAME = uploadImg;

    public static class Success {
        private final static boolean defaultFlag = true;
        public static String uploadImg = doUploadImg(KEY_FUNC_MODULE_NAME,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String uploadImg = doUploadImg(KEY_FUNC_MODULE_NAME,defaultFlag);
    }
}
