package com.egg.manager.api.trait.utils.oss;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import com.egg.manager.api.config.oss.AliyunOssConfig;
import com.egg.manager.persistence.commons.base.beans.oss.AliyunOssResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 阿里云OSS工具类
 * @author jason
 */
@Slf4j
public class AliyunOSSUtil {

    /**
     * oss 工具客户端
     */
    private static OSSClient ossClient = null;

    /**
     * 单例模式
     * 初始化 oss 客户端
     */
    private static OSSClient initOSS() {
        if (ossClient == null) {
            synchronized (AliyunOSSUtil.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient(AliyunOssConfig.JAVA_END_POINT,
                            new DefaultCredentialProvider(AliyunOssConfig.JAVA_ACCESS_KEY_ID, AliyunOssConfig.JAVA_ACCESS_KEY_SECRET),
                            new ClientConfiguration());
                }
            }
        }
        return ossClient;
    }

    /**
     * 上传文件-自定义路径
     * @param file     上传文件
     * @param fileName 上传至OSS的文件完整路径，例：cf/abc.png
     *                 上传至根目录，例：abc.png
     * @return
     */
    public static AliyunOssResult uploadFile(File file, String fileName) {
        // 文件流
        InputStream inputStream = FileUtil.getInputStream(file);
        // 获取文件类型
        String fileType = FileUtil.getType(file);

        // 上传文件
        return uploadInputStream(inputStream, fileType, fileName);
    }

    /**
     * 上传文件-自定义路径
     * @param file     上传文件
     * @param fileName 上传至OSS的文件完整路径，例：cf/abc.png
     *                 上传至根目录，例：abc.png
     * @return
     */
    public static AliyunOssResult uploadFile(MultipartFile file, String fileName) {
        // 文件流
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("执行异常--->", e);
            return new AliyunOssResult(false, null, null, e.getMessage());
        }
        // 获取文件类型
        String fileType = file.getContentType();

        // 上传文件
        return uploadInputStream(inputStream, fileType, fileName);
    }

    /**
     * 上传文件-自定义路径
     * @param inputStream 上传文件流
     * @param fileType    文件类型，例：png
     * @param fileName    上传至OSS的文件完整路径，例：cf/abc.png
     *                    上传至根目录，例：abc.png
     * @return
     */
    public static AliyunOssResult uploadInputStream(InputStream inputStream, String fileType, String fileName) {
        if (inputStream == null) {
            return new AliyunOssResult(false, null, null, "文件不能为空");
        }
        if (StrUtil.isBlank(fileName)) {
            return new AliyunOssResult(false, null, null, "文件名不能为空");
        }
        // 上传文件最大值 MB->bytes
        long maxSize = AliyunOssConfig.JAVA_MAX_SIZE * 1024 * 1024;
        // 本次上传文件的大小
        long fileSize = 0;
        try {
            fileSize = inputStream.available();
        } catch (IOException e) {
            log.error("执行异常--->", e);
        }
        if (fileSize <= 0 || fileSize > maxSize) {
            return new AliyunOssResult(false, null, null, "文件超过最大限制");
        }

        // 上传文件
        return putFile(inputStream, fileType, fileName);
    }

    /**
     * 上传文件
     * @param input
     * @param fileType
     * @param fileName
     * @return
     */
    private static AliyunOssResult putFile(InputStream input, String fileType, String fileName) {
        ossClient = initOSS();
        try {
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(fileType);
            //被下载时网页的缓存行为
            meta.setCacheControl("no-cache");
            //创建上传请求
            PutObjectRequest request = new PutObjectRequest(AliyunOssConfig.JAVA_BUCKET_NAME, fileName, input, meta);
            //上传文件
            ossClient.putObject(request);

            //获取上传成功的文件地址
            return new AliyunOssResult(true, fileName, getOssUrl(fileName), "上传成功");
        } catch (OSSException | ClientException e) {
            log.error("执行异常--->", e);
            return new AliyunOssResult(false, fileName, null, e.getMessage());
        }
    }

    /**
     * 根据文件名生成文件的访问地址（带过期时间）
     * @param fileName
     * @return
     */
    public static String getOssUrl(String fileName) {
        ossClient = initOSS();
        // 生成过期时间
        long expireEndTime = System.currentTimeMillis() + AliyunOssConfig.JAVA_POLICY_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);// 生成URL
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(AliyunOssConfig.JAVA_BUCKET_NAME, fileName);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 通过文件名下载文件
     * @param fileName      要下载的文件名（OSS服务器上的）
     *                      例如：4DB049D0604047989183CB68D76E969D.jpg
     * @param localFileName 本地要创建的文件名（下载到本地的）
     *                      例如：C:\Users\Administrator\Desktop\test.jpg
     */
    public static void downloadFile(String fileName, String localFileName) {
        ossClient = initOSS();
        // 下载OSS文件到指定目录。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(AliyunOssConfig.JAVA_BUCKET_NAME, fileName), new File(localFileName));
    }

    /**
     * 通过文件名获取文件流
     * @param fileName 要下载的文件名（OSS服务器上的）
     *                 例如：4DB049D0604047989183CB68D76E969D.jpg
     */
    public static InputStream getInputStream(String fileName) {
        ossClient = initOSS();
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        return ossClient.getObject(new GetObjectRequest(AliyunOssConfig.JAVA_BUCKET_NAME, fileName)).getObjectContent();
    }

    /**
     * 通过文件名获取byte[]
     * @param fileName 要下载的文件名（OSS服务器上的）
     *                 例如：4DB049D0604047989183CB68D76E969D.jpg
     */
    public static byte[] getBytes(String fileName) {
        InputStream inputStream = getInputStream(fileName);
        FastByteArrayOutputStream fastByteArrayOutputStream = IoUtil.read(inputStream);
        return fastByteArrayOutputStream.toByteArray();
    }

    /**
     * 根据文件名删除文件
     * @param fileName 需要删除的文件名
     * @return boolean 是否删除成功
     * 例如：4DB049D0604047989183CB68D76E969D.jpg
     */
    public static boolean deleteFile(String fileName) {
        ossClient = initOSS();
        try {
            if (AliyunOssConfig.JAVA_BUCKET_NAME == null || fileName == null) return false;
            GenericRequest request = new DeleteObjectsRequest(AliyunOssConfig.JAVA_BUCKET_NAME).withKey(fileName);
            ossClient.deleteObject(request);
        } catch (Exception e) {
            log.error("执行异常--->", e);
            return false;
        }
        return true;
    }

    /**
     * 列举所有的文件url
     */
    public static List<String> urlList() {
        ossClient = initOSS();
        List<String> list = new ArrayList<>();
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(AliyunOssConfig.JAVA_BUCKET_NAME);
        // 列出文件
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            // 把key全部转化成可以访问的url
            String url = getOssUrl(objectSummary.getKey());
            list.add(url);
        }
        return list;
    }

}
