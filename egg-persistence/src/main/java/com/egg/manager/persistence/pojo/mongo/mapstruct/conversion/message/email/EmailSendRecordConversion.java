package com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.message.email;

import cn.hutool.core.collection.CollectionUtil;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailFromUserInfoMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailReceiveUserInfoMgvo;
import com.google.common.collect.Lists;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoucj
 * @description:表单类型
 * @date 2020/10/21
 */
@Component
@Named("messageEmailConversion")
public class EmailSendRecordConversion {


    /**
     * 取得-发送人-邮箱地址
     * @param fromUserInfoMVO
     * @return
     */
    @Named("handleGetFromUserEmailAddress")
    public String handleGetFromUserEmailAddress(EmailFromUserInfoMgvo fromUserInfoMVO) {
        if (fromUserInfoMVO == null || fromUserInfoMVO.getEmailAddress() == null) {
            return "";
        }
        return fromUserInfoMVO.getEmailAddress();
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
        for (EmailReceiveUserInfoMgvo receiveUserInfoMVO : receiveUserInfoList) {
            if (receiveUserInfoMVO != null) {
                if (receiveUserInfoMVO.getUserName() != null) {
                    list.add(receiveUserInfoMVO.getUserName());
                }
            }
        }
        return list.stream().toArray(String[]::new);
    }
}
