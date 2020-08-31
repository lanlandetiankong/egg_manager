package com.egg.manager.common.base.props.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 18:24
 * \* Description:
 * \
 */
@Component
@ConfigurationProperties(prefix = "props.upload")
public class UploadProps implements Serializable{
    /**
     * 访问路径前缀
     */
    private String urlPrefix ;
    /**
     * 路径位置前缀
     */
    private String locationPrefix ;
    /**
     * 图片存放路径
     */
    private String locationOfImg ;
    /**
     * excel存放路径
     */
    private String locationOfExcel ;
    /**
     * 项目名
     */
    private String projectName ;



    //setter 、getter

    public String getLocationOfExcel() {
        return locationOfExcel;
    }

    public void setLocationOfExcel(String locationOfExcel) {
        this.locationOfExcel = locationOfExcel;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getLocationPrefix() {
        return locationPrefix;
    }

    public void setLocationPrefix(String locationPrefix) {
        this.locationPrefix = locationPrefix;
    }

    public String getLocationOfImg() {
        return locationOfImg;
    }

    public void setLocationOfImg(String locationOfImg) {
        this.locationOfImg = locationOfImg;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
