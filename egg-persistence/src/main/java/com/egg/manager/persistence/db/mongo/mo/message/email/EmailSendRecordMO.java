package com.egg.manager.persistence.db.mongo.mo.message.email;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailFromUserInfoMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailSendFileInfoMVO;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: EmailSendRecordMO
 * @Author: zhoucj
 * @Date: 2020/9/11 14:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "email_send_record")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class EmailSendRecordMO extends MyBaseModelMO<String> {

    /**
     * 发送者-相关信息
     */
    private EmailFromUserInfoMVO fromUserInfo ;
    /**
     * 主题
     */
    private String subject;
    /**
     * 接收方邮件(必填参数)
     */
    private List<EmailFromUserInfoMVO> receiveUserInfoList ;
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
    private Map<String, String> kvMap;
    /**
     * 附件信息-列表
     */
    private List<EmailSendFileInfoMVO> accessoryInfoList ;
    /**
     * 是否发送成功？
     */
    private Boolean successFlag = true ;


    /**
     * 发送日期
     */
    private Date sentDate ;
}
