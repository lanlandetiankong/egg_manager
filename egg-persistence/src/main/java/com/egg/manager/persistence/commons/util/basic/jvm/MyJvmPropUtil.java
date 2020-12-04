package com.egg.manager.persistence.commons.util.basic.jvm;


import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author zhoucj
 * @description启动参数 工具类
 * @date 2020/10/21
 */
public class MyJvmPropUtil {
    public static final String ENV_OF_DEV = "dev";
    public static final String ENV_OF_TEST = "test";
    public static final String ENV_OF_PROD = "prod";


    /**
     * 取得 jvm启动时的所有参数
     * @return
     */
    public static List<String> getAllJvmParamsList() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments();
    }


    /**
     * 取得 jvm启动时的 -d参数
     * @param key        参数名
     * @param defaultVal 默认值
     * @return
     */
    public static String getDefineParams(String key, String defaultVal) {
        List<String> argus = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (String arg : argus) {
            if (StringUtils.isNotBlank(arg)) {
                if (arg.indexOf("-D" + key) >= 0) {
                    return arg.substring(key.length() + 3).trim();
                }
            }
        }
        return defaultVal;
    }

    /**
     * 取得 jvm启动时的 -d参数
     * @return dev:开发环境、prod:生产环境、test:测试环境
     */
    public static String getSpringProfilesActive() {
        return getDefineParams("spring.profiles.active", "dev");
    }

    /**
     * 判断当前项目运行环境是否存在匹配
     * @param envList 需要判断的 环境值
     * @return
     */
    public static boolean judgeRunInTestEnv(String... envList) {
        if (envList == null || envList.length == 0) {
            return false;
        }
        boolean flag = false;
        for (String env : envList) {
            if (StringUtils.isNotEmpty(env)) {
                flag = env.equalsIgnoreCase(getSpringProfilesActive());
            }
            if (flag) {
                break;
            }
        }
        return flag;
    }

}
