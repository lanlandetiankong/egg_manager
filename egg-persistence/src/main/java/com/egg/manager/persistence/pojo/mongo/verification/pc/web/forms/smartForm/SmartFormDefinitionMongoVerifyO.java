package com.egg.manager.persistence.pojo.mongo.verification.pc.web.forms.smartForm;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.MyBaseMongoVerifyO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * \* note: SmartFormDefinitionMO 对应的验证对象
 * \* User: zhouchengjie
 * \* Date: 2020/7/25
 * \* Time: 11:06
 * \* Description:
 *
 * @see com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO
 * \
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmartFormDefinitionMongoVerifyO extends MyBaseMongoVerifyO<SmartFormDefinitionMO> {
    @NotBlank(groups = {VerifyGroupOfUpdate.class}, message = "[fid]不能为空!")
    private String fid;

    /**
     * 表单标题
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[标题]不能为空!")
    private String title;
    /**
     * 表单类型
     */
    @NotNull
    private SmartFormTypeDefinitionMO formType;

    /**
     * 描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[描述]不能为空!")
    private String description;

    /**
     * 顺序
     */
    private Integer orderNum;
    private String remark;

    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[表单类型]不能为空!")
    private String formTypeId;


}
