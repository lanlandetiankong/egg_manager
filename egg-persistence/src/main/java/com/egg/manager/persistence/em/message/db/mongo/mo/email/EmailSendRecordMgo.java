package com.egg.manager.persistence.em.message.db.mongo.mo.email;

import com.egg.manager.persistence.expand.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailFromUserInfoMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailReceiveUserInfoMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailSendFileInfoMgvo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
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
public class EmailSendRecordMgo extends MyBaseModelMgo<Long> {

    /**
     * 发送者-相关信息
     */
    private EmailFromUserInfoMgvo fromUserInfo;
    /**
     * 主题
     */
    private String subject;
    /**
     * 接收方邮件(必填参数)
     */
    private List<EmailReceiveUserInfoMgvo> receiveUserInfoList;
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
    private List<EmailSendFileInfoMgvo> accessoryInfoList;
    /**
     * 是否发送成功？
     */
    private Boolean successFlag = true;


    /**
     * 发送日期
     */
    private Date sentDate;
}
