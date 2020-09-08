package com.egg.manager.common.base.props.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * \* note: 静态方法可以调用 props-upload.properties配置的参数
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 18:24
 * \* Description:
 * \
 */
@Component
@ConfigurationProperties(prefix = "props.upload")
public class UploadStaticProps implements Serializable {
    /**
     * 访问路径前缀
     */
    private static String urlPrefix ;
    /**
     * 路径位置前缀
     */
    private static String locationPrefix ;
    /**
     * 图片存放路径
     */
    private static String locationOfImg ;
    /**
     * excel存放路径
     */
    private static String locationOfExcel ;
    /**
     * 项目名
     */
    private static String projectName ;




    //setter

    @Value("${props.upload.urlPrefix}")
    public void setUrlPrefix(String urlPrefix) {
        UploadStaticProps.urlPrefix = urlPrefix;
    }
    @Value("${props.upload.locationPrefix}")
    public void setLocationPrefix(String locationPrefix) {
        UploadStaticProps.locationPrefix = locationPrefix;
    }
    @Value("${props.upload.locationOfImg}")
    public void setLocationOfImg(String locationOfImg) {
        UploadStaticProps.locationOfImg = locationOfImg;
    }
    @Value("${props.upload.locationOfExcel}")
    public void setLocationOfExcel(String locationOfExcel) {
        UploadStaticProps.locationOfExcel = locationOfExcel;
    }
    @Value("${props.upload.projectName}")
    public void setProjectName(String projectName) {
        UploadStaticProps.projectName = projectName;
    }

    //getter

    public static String getUrlPrefix() {
        return UploadStaticProps.urlPrefix;
    }

    public static String getLocationPrefix() {
        return UploadStaticProps.locationPrefix;
    }

    public static String getLocationOfImg() {
        return UploadStaticProps.locationOfImg;
    }

    public static String getLocationOfExcel() {
        return UploadStaticProps.locationOfExcel;
    }

    public static String getProjectName() {
        return UploadStaticProps.projectName;
    }
}
