package com.egg.manager.facade.persistence.em.message.pojo.mvo.email;

import cn.hutool.core.collection.CollectionUtil;
import com.egg.manager.facade.persistence.em.message.pojo.mvo.email.other.EmailFromUserInfoMgvo;
import com.egg.manager.facade.persistence.em.message.pojo.mvo.email.other.EmailReceiveUserInfoMgvo;
import com.egg.manager.facade.persistence.em.message.pojo.mvo.email.other.EmailSendFileInfoMgvo;
import com.egg.manager.facade.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmailSendRecordMgvo extends BaseModelMgvo<Long> {

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
     * 附件-信息-列表(可选)
     */
    private List<EmailSendFileInfoMgvo> accessoryInfoList;
    /**
     * 是否发送成功？
     */
    private Boolean successFlag;
    /**
     * 发送日期
     */
    private Date sentDate;


    /**
     * 取得-接收人-姓名-集合
     * @return
     */
    public List<String> doGainReceiveUserNameList() {
        List<String> list = Lists.newArrayList();
        if (CollectionUtil.isEmpty(this.receiveUserInfoList)) {
            return list;
        }
        for (EmailReceiveUserInfoMgvo receiveUserInfoMgvo : this.receiveUserInfoList) {
            if (receiveUserInfoMgvo != null) {
                if (receiveUserInfoMgvo.getUserName() != null) {
                    list.add(receiveUserInfoMgvo.getUserName());
                }
            }
        }
        return list;
    }
}
