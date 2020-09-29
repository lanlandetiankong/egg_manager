package com.egg.manager.persistence.pojo.mongo.mvo.message.email;

import cn.hutool.core.collection.CollectionUtil;
import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailFromUserInfoMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailReceiveUserInfoMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.other.EmailSendFileInfoMVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhoucj
 * @Date: 2020/9/11 14:55
 */
@Data
@Builder
public class EmailSendRecordMVO extends BaseModelMVO<String> {

    /**
     * 发送者-相关信息
     */
    private EmailFromUserInfoMVO fromUserInfo;
    /**
     * 主题
     */
    private String subject;
    /**
     * 接收方邮件(必填参数)
     */
    private List<EmailReceiveUserInfoMVO> receiveUserInfoList;
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
    private List<EmailSendFileInfoMVO> accessoryInfoList;
    /**
     * 是否发送成功？
     */
    private Boolean successFlag ;
    /**
     * 发送日期
     */
    private Date sentDate;

    public EmailSendRecordMVO() {
        this.successFlag = true ;
    }

    /**
     * 取得-接收人-姓名-集合
     *
     * @return
     */
    public List<String> doGainReceiveUserNameList() {
        List<String> list = Lists.newArrayList();
        if (CollectionUtil.isEmpty(this.receiveUserInfoList)) {
            return list;
        }
        for (EmailReceiveUserInfoMVO receiveUserInfoMVO : this.receiveUserInfoList) {
            if (receiveUserInfoMVO != null) {
                if (receiveUserInfoMVO.getUserName() != null) {
                    list.add(receiveUserInfoMVO.getUserName());
                }
            }
        }
        return list;
    }
}
