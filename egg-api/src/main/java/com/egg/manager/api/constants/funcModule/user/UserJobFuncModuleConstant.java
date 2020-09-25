package com.egg.manager.api.constants.funcModule.user;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: DefineDepartmentFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:13
 */
public class UserJobFuncModuleConstant  extends EggBaseFuncModuleNameConstant {
    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(userJob,defaultFlag);
        public static String update = doCreate(userJob,defaultFlag);
        public static String deleteById = doDeleteById(userJob,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(userJob,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(userJob,defaultFlag);
        public static String update = doCreate(userJob,defaultFlag);
        public static String deleteById = doDeleteById(userJob,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(userJob,defaultFlag);
    }
}
