package com.egg.manager.common.base.beans.file;

import lombok.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/17
 * \* Time: 14:32
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
public class FileResBean {
    private String fileName ;
    private String fileLocation ;
    private String filePrefix ;
    private String fileUri ;
    private String fileOldName ;




}
