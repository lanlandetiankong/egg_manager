package com.egg.manager.persistence.pojo.mongo.mvo.message.email.other;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description: email-接收者-相关信息
 * @date 2020/10/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
