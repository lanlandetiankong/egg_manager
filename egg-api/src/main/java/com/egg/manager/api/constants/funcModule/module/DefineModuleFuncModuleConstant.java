package com.egg.manager.api.constants.funcModule.module;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class DefineModuleFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(defineModule,defaultFlag);
        public static String update = doCreate(defineModule,defaultFlag);
        public static String deleteById = doDeleteById(defineModule,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineModule,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(defineModule,defaultFlag);
        public static String update = doCreate(defineModule,defaultFlag);
        public static String deleteById = doDeleteById(defineModule,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineModule,defaultFlag);
    }

}
