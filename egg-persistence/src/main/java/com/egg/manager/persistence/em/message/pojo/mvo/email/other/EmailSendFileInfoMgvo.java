package com.egg.manager.persistence.em.message.pojo.mvo.email.other;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhoucj
 * @description email-发送的附件-文件信息
 * @date 2020/10/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendFileInfoMgvo extends BaseMgvo {

    /**
     * 文件-原名
     */
    private String originalName;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件-相对路径
     */
    private String relativePath;
    /**
     * 文件名-后缀
     */
    private String fileExtension;
    /**
     * 文件-大小
     */
    private Long fileSize;


}
