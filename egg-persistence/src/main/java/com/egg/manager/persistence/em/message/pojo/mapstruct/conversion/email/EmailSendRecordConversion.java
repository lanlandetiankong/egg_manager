package com.egg.manager.persistence.em.message.pojo.mapstruct.conversion.email;

import cn.hutool.core.collection.CollectionUtil;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailFromUserInfoMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailReceiveUserInfoMgvo;
import com.google.common.collect.Lists;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoucj
 * @description表单类型
 * @date 2020/10/21
 */
@Component
@Named("messageEmailConversion")
public class EmailSendRecordConversion {


    /**
     * 取得-发送人-邮箱地址
     * @param fromUserInfoMgvo
     * @return
     */
    @Named("handleGetFromUserEmailAddress")
    public String handleGetFromUserEmailAddress(EmailFromUserInfoMgvo fromUserInfoMgvo) {
        if (fromUserInfoMgvo == null || fromUserInfoMgvo.getEmailAddress() == null) {
            return "";
        }
        return fromUserInfoMgvo.getEmailAddress();
    }

    /**
     * 取得-发送人-邮箱地址
     * @param receiveUserInfoList
     * @return
     */
    @Named("handleGetReceiveUserAddressList")
    public String[] handleGetReceiveUserAddressList(List<EmailReceiveUserInfoMgvo> receiveUserInfoList) {
        List<String> list = Lists.newArrayList();
        if (CollectionUtil.isEmpty(receiveUserInfoList)) {
            return new String[]{};
        }
        for (EmailReceiveUserInfoMgvo receiveUserInfoMgvo : receiveUserInfoList) {
            if (receiveUserInfoMgvo != null) {
                if (receiveUserInfoMgvo.getUserName() != null) {
                    list.add(receiveUserInfoMgvo.getUserName());
                }
            }
        }
        return list.stream().toArray(String[]::new);
    }
}
