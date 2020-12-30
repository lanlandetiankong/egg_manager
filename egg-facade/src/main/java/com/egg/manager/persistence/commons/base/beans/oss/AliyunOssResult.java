package com.egg.manager.persistence.commons.base.beans.oss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云OSS上传结果集
 * @author jason
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AliyunOssResult {

    /**
     * 上传是否成功
     */
    private boolean success;

    /**
     * 上传的文件名（如果使用自定义文件路径，会返回完整的路径+文件名，例：cf/abc.png）
     */
    private String fileName;

    /**
     * 上传成功的返回url（带过期时间）
     */
    private String url;

    /**
     * 提示信息
     */
    private String msg;

    public static AliyunOssResult error() {
        return AliyunOssResult.builder().success(false).build();
    }

    public static AliyunOssResult error(String msg) {
        return AliyunOssResult.builder().success(false).msg(msg).build();
    }

    public static AliyunOssResult error(String fileName, String msg) {
        return AliyunOssResult.builder().success(false).fileName(fileName).msg(msg).build();
    }

    public static AliyunOssResult success() {
        return AliyunOssResult.builder().success(true).build();
    }

    public static AliyunOssResult success(String fileName, String url, String msg) {
        return AliyunOssResult.builder().success(true).fileName(fileName).url(url).msg(msg).build();
    }
}