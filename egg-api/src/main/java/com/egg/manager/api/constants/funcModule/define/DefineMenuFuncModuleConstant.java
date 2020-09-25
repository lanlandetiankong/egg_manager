package com.egg.manager.api.constants.funcModule.define;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class DefineMenuFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(defineMenu,defaultFlag);
        public static String update = doCreate(defineMenu,defaultFlag);
        public static String deleteById = doDeleteById(defineMenu,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineMenu,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(defineMenu,defaultFlag);
        public static String update = doCreate(defineMenu,defaultFlag);
        public static String deleteById = doDeleteById(defineMenu,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineMenu,defaultFlag);
    }
}
