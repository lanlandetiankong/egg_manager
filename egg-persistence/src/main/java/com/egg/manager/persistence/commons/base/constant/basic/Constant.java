package com.egg.manager.persistence.commons.base.constant.basic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class Constant implements Serializable {

    public static final int BYTE_BUFFER = 1024;

    public static Set<String> METHOD_URL_SET = new HashSet<>();


    public static final int BUFFER_MULTIPLE = 10;

    /**
     * 验证码过期时间
     */
    public static final Long PASS_TIME = 50000 * 60 * 1000L;


    /**
     * 用户名登录
     */
    public static final int LOGIN_USERNAME = 0;
    /**
     * 手机登录
     */
    public static final int LOGIN_MOBILE = 1;
    /**
     * 邮箱登录
     */
    public static final int LOGIN_EMAIL = 2;

    /**
     * 空json对象
     */
    public final static String JSON_EMPTY_OBJECT = "{}";
    /**
     * 空json数组
     */
    public final static String JSON_EMPTY_ARRAY = "[]";
    /**
     * 空json数组 字符串
     */
    public final static String JSON_EMPTY_ARRAY_STR = "\"[]\"";
    /**
     * @ 符号
     */
    public final static String SYMBOL_AT = "@";
    /**
     * 斜杠 /
     */
    public final static String SYMBOL_SLASH = "/";


    public static class FilePostFix {
        public static final String ZIP_FILE = ".zip";
        public static final String[] IMAGES = {"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
        public static final String[] ZIP = {"ZIP", "zip", "rar", "RAR"};
        public static final String[] VIDEO = {"mp4", "MP4", "mpg", "mpe", "mpa", "m15", "m1v", "mp2", "rmvb"};
        public static final String[] APK = {"apk", "exe"};
        public static final String[] OFFICE = {"xls", "xlsx", "docx", "doc", "ppt", "pptx"};

    }

    public class FileType {
        public static final int FILE_IMG = 1;
        public static final int FILE_ZIP = 2;
        public static final int FILE_VEDIO = 3;
        public static final int FILE_APK = 4;
        public static final int FIVE_OFFICE = 5;
        public static final String FILE_IMG_DIR = "/img/";
        public static final String FILE_ZIP_DIR = "/zip/";
        public static final String FILE_VEDIO_DIR = "/video/";
        public static final String FILE_APK_DIR = "/apk/";
        public static final String FIVE_OFFICE_DIR = "/office/";
    }


}
