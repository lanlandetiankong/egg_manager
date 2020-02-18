package com.egg.manager.common.base.props.upload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
@PropertySource("classpath:props-upload.properties")
public class UploadProps {


    @Value("urlPrefix")
    private String urlPrefix ;
    //define
    @Value("locationPrefix")
    private String locationPrefix ;

    @Value("locationOfImg")
    private String locationOfImg ;

    @Value("projectName")
    private String projectName ;













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
