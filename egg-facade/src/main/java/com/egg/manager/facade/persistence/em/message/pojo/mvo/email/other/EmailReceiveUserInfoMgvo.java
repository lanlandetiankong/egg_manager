package com.egg.manager.facade.persistence.em.message.pojo.mvo.email.other;

import com.egg.manager.facade.persistence.exchange.pojo.mongo.mvo.BaseMgvo;
import lombok.*;

/**
 * @author zhoucj
 * @description email-接收者-相关信息
 * @date 2020/10/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailReceiveUserInfoMgvo extends BaseMgvo {
    /**
     * 发送者-账号id
     */
    private String userAccountId;
    /**
     * 发送者-用户名
     */
    private String userName;
    /**
     * 发送者-邮箱地址
     */
    private String emailAddress;


}
