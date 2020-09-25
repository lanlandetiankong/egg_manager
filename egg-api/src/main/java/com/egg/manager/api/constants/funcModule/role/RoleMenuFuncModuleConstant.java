package com.egg.manager.api.constants.funcModule.role;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class RoleMenuFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(roleMenu,defaultFlag);
        public static String update = doCreate(roleMenu,defaultFlag);
        public static String deleteById = doDeleteById(roleMenu,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(roleMenu,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(roleMenu,defaultFlag);
        public static String update = doCreate(roleMenu,defaultFlag);
        public static String deleteById = doDeleteById(roleMenu,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(roleMenu,defaultFlag);
    }
}
