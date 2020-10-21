package com.egg.manager.common.base.beans.file;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.egg.manager.common.base.props.upload.UploadStaticProps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AntdFileUploadBean implements Serializable {
    /**
     * uid
     */
    private String uid;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件地址
     */
    @JSONField(serialize = false)
    private String url;
    /**
     * 状态
     */
    private String status;
    private String response;
    private Object linkProps;
    private String xhr;

    /**
     * uri
     */
    private String uri;
    /**
     * uri路径
     */
    private String urlLocation;


    public static AntdFileUploadBean dealJsonStrToBean(String jsonStr) {
        if (StringUtils.isNotBlank(jsonStr)) {
            AntdFileUploadBean fileUploadBean = JSONObject.parseObject(jsonStr, AntdFileUploadBean.class);
            if (StringUtils.isBlank(fileUploadBean.getUri())) {
                fileUploadBean.setUrl(null);
            } else {
                fileUploadBean.setUrl(UploadStaticProps.getUrlPrefix() + fileUploadBean.getUri());
            }
            if (StringUtils.isBlank(fileUploadBean.getXhr())) {
                fileUploadBean.setResponse(null);
            }
            Object linkProps = fileUploadBean.getLinkProps();
            if (linkProps == null) {
                fileUploadBean.setResponse(null);
            } else {
                if (linkProps instanceof String) {
                    //不可以是 string
                    fileUploadBean.setLinkProps(null);
                }
            }
            return fileUploadBean;
        }
        return null;
    }

}
