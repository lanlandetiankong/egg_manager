package com.egg.manager.facade.persistence.em.forms.pojo.verification.smartform;

import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.facade.persistence.exchange.pojo.mongo.verification.BaseMongoVerifyO;
import com.egg.manager.facade.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.facade.persistence.exchange.verification.igroup.VerifyGroupOfUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhoucj
 * @descriptionSmartFormDefinitionMgo 对应的验证对象
 * @date 2020/10/21
 * @see SmartFormDefinitionMgo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmartFormDefinitionMongoVerifyO extends BaseMongoVerifyO<SmartFormDefinitionMgo> {
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
    private SmartFormTypeDefinitionMgo formType;

    /**
     * 描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[描述]不能为空!")
    private String description;

    /**
     * 顺序
     */
    private Integer weights;
    private String remark;

    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[表单类型]不能为空!")
    private String formTypeId;


}
