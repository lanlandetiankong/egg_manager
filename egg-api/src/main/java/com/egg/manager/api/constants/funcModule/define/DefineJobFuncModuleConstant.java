package com.egg.manager.api.constants.funcModule.define;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class DefineJobFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(defineJob,defaultFlag);
        public static String update = doCreate(defineJob,defaultFlag);
        public static String deleteById = doDeleteById(defineJob,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineJob,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(defineJob,defaultFlag);
        public static String update = doCreate(defineJob,defaultFlag);
        public static String deleteById = doDeleteById(defineJob,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineJob,defaultFlag);
    }
}
