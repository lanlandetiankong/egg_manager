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
    private String fileName ;
    private String fileLocation ;
    private String filePrefix ;
    private String fileUri ;
    private String fileOldName ;




}
