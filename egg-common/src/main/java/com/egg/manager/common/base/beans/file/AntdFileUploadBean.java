package com.egg.manager.common.base.beans.file;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.egg.manager.common.base.props.upload.UploadStaticProps;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/4
 * \* Time: 14:44
 * \* Description:
 * \
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AntdFileUploadBean {

    private String uid ;
    private String name ;
    @JSONField(serialize = false)
    private String url ;

    private String status ;
    private String response ;
    private Object linkProps ;
    private String xhr ;


    private String uri ;
    private String urlLocation ;




    public static AntdFileUploadBean dealJsonStrToBean(String jsonStr){
        if(StringUtils.isNotBlank(jsonStr)){
            AntdFileUploadBean fileUploadBean = JSONObject.parseObject(jsonStr,AntdFileUploadBean.class);
            if(StringUtils.isBlank(fileUploadBean.getUri())){
                fileUploadBean.setUrl(null);
            }   else {
                fileUploadBean.setUrl(UploadStaticProps.getUrlPrefix() +fileUploadBean.getUri());
            }
            if(StringUtils.isBlank(fileUploadBean.getXhr())){
                fileUploadBean.setResponse(null);
            }
            Object linkProps = fileUploadBean.getLinkProps();
            if(linkProps == null){
                fileUploadBean.setResponse(null);
            }   else {
                if(linkProps instanceof String){    //不可以是 string
                    fileUploadBean.setLinkProps(null);
                }
            }
            return fileUploadBean ;
        }
        return null ;
    }

}