package com.egg.manager.api.constants.funcModule.user;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class UserDepartmentFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(userDepartment,defaultFlag);
        public static String update = doCreate(userDepartment,defaultFlag);
        public static String deleteById = doDeleteById(userDepartment,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(userDepartment,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(userDepartment,defaultFlag);
        public static String update = doCreate(userDepartment,defaultFlag);
        public static String deleteById = doDeleteById(userDepartment,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(userDepartment,defaultFlag);
    }
}
