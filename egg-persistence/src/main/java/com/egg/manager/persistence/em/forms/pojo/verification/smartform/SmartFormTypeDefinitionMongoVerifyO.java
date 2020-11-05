package com.egg.manager.persistence.em.forms.pojo.verification.smartform;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.enhance.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.enhance.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.enhance.pojo.mongo.verification.BaseMongoVerifyO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoucj
 * @description: SmartFormTypeDefinitionMgo 对应的验证对象
 * @date 2020/10/21
 * @see SmartFormTypeDefinitionMgo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmartFormTypeDefinitionMongoVerifyO extends BaseMongoVerifyO<SmartFormTypeDefinitionMgo> {
    @NotBlank(groups = {VerifyGroupOfUpdate.class}, message = "[fid]不能为空!")
    private String fid;

    /**
     * 顺序
     */
    private Integer orderNum;
    /**
     * 类型名
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[类型名]不能为空!")
    private String name;
    /**
     * 类型描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[描述]不能为空!")
    private String description;
    private String remark;


}
