package com.egg.manager.persistence.pojo.common.message.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Email封装类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyEmailMsgO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 接收方邮件(必填参数)
     */
    private String[] receiveEmails;
    /**
     * 主题
     */
    private String subject;
    /**
     * 发送者(可选，service里应该处理默认发送人)
     */
    private String fromUser;
    /**
     * 发送者(可选，个性化)
     */
    private String fromUserPersonal;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 模板(选填)
     */
    private String template;
    /**
     * 自定义参数
     */
    private HashMap<String, String> kvMap;
    /**
     * 携带文件附件(可选)
     */
    private List<File> fileList;


}
