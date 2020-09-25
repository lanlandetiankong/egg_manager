package com.egg.manager.api.constants.funcModule.announcement;

import com.egg.manager.api.constants.funcModule.EggBaseFuncModuleNameConstant;

/**
 * @Description:
 * @ClassName: AnnouncementFuncModuleConstant
 * @Author: zhoucj
 * @Date: 2020/9/25 14:10
 */
public class AnnouncementFuncModuleConstant extends EggBaseFuncModuleNameConstant {

    public static class Success {
        private final static boolean defaultFlag = true;
        public static String create = doCreate(announcement,defaultFlag);
        public static String update = doCreate(announcement,defaultFlag);
        public static String deleteById = doDeleteById(announcement,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(announcement,defaultFlag);
    }

    public static class Failure {
        private final static boolean defaultFlag = false;
        public static String create = doCreate(announcement,defaultFlag);
        public static String update = doCreate(announcement,defaultFlag);
        public static String deleteById = doDeleteById(announcement,defaultFlag);
        public static String batchDeleteByIds = doBatchDeleteByIds(announcement,defaultFlag);
    }
}
