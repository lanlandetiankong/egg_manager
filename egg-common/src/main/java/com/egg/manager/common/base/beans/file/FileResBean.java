package com.egg.manager.common.base.beans.file;

import lombok.*;

import java.io.Serializable;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/17
 * \* Time: 14:32
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResBean  implements Serializable {
    /**
     * 文件名
     */
    private String fileName ;
    /**
     * 文件位置
     */
    private String fileLocation ;
    /**
     * 文件名前缀
     */
    private String filePrefix ;
    /**
     * 图片uri
     */
    private String fileUri ;
    /**
     * 原文件名
     */
    private String fileOldName ;




}
