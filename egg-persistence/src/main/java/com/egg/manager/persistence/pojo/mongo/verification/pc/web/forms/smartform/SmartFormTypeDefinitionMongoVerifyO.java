package com.egg.manager.persistence.pojo.mongo.verification.pc.web.forms.smartform;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfUpdate;
import com.egg.manager.persistence.pojo.mongo.verification.pc.web.BaseMongoVerifyO;
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
