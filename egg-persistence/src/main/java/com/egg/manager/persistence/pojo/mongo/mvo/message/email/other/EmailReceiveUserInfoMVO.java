package com.egg.manager.persistence.pojo.mongo.mvo.message.email.other;

import com.egg.manager.persistence.pojo.mongo.mvo.MyBaseMVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: email-接收者-相关信息
 * @ClassName: EmailFromUserInfoMVO
 * @Author: zhoucj
 * @Date: 2020/9/11 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailReceiveUserInfoMVO  extends MyBaseMVO {
    /**
     * 发送者-账号id
     */
    private String userAccountId ;
    /**
     * 发送者-用户名
     */
    private String userName ;
    /**
     * 发送者-邮箱地址
     */
    private String emailAddress ;


}
