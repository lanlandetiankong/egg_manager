package com.egg.manager.persistence.pojo.mongo.mvo.message.email.other;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description: email-发送的附件-文件信息
 * @ClassName: EmailSendFileInfoMgvo
 * @Author: zhoucj
 * @Date: 2020/9/11 15:18
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
