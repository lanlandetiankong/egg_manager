package com.egg.manager.persistence.em.message.pojo.verification.email;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.enhance.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.enhance.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailFromUserInfoMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailReceiveUserInfoMgvo;
import com.egg.manager.persistence.em.message.pojo.mvo.email.other.EmailSendFileInfoMgvo;
import com.egg.manager.persistence.enhance.pojo.mongo.verification.BaseMongoVerifyO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description:SmartFormDefinitionMgo 对应的验证对象
 * @date 2020/10/21
 * @see SmartFormDefinitionMgo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailSendRecordMongoVerifyO extends BaseMongoVerifyO {


    @NotBlank(groups = {VerifyGroupOfUpdate.class}, message = "[fid]不能为空!")
    private String fid;

    /**
     * 发送者-相关信息
     */
    @NotNull(groups = {VerifyGroupOfDefault.class}, message = "[发送者-相关信息]不能为空!")
    private EmailFromUserInfoMgvo fromUserInfo;
    /**
     * 主题
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[主题]不能为空!")
    private String subject;
    /**
     * 接收方-相关信息(必填参数)
     */
    @NotEmpty(groups = {VerifyGroupOfDefault.class}, message = "[接收方-相关信息]不能为空!")
    private List<EmailReceiveUserInfoMgvo> receiveUserInfoList;
    /**
     * 邮件内容
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[邮件内容]不能为空!")
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
    private Boolean successFlag = true;
    /**
     * 发送日期
     */
    private Date sentDate;

}
