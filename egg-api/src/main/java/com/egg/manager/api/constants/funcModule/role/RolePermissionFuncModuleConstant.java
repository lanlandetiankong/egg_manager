package com.egg.manager.api.constants.funcModule.role;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class RolePermissionFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(rolePermission,defaultFlag);
        public static String update = doCreate(rolePermission,defaultFlag);
        public static String deleteById = doDeleteById(rolePermission,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(rolePermission,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(rolePermission,defaultFlag);
        public static String update = doCreate(rolePermission,defaultFlag);
        public static String deleteById = doDeleteById(rolePermission,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(rolePermission,defaultFlag);
    }
}
