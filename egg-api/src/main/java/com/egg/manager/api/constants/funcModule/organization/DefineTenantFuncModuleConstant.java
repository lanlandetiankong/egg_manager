package com.egg.manager.api.constants.funcModule.organization;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class DefineTenantFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(defineTenant,defaultFlag);
        public static String update = doCreate(defineTenant,defaultFlag);
        public static String deleteById = doDeleteById(defineTenant,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineTenant,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(defineTenant,defaultFlag);
        public static String update = doCreate(defineTenant,defaultFlag);
        public static String deleteById = doDeleteById(defineTenant,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(defineTenant,defaultFlag);
    }
    
}
