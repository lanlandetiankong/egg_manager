package com.egg.manager.web.verification.mongodb.forms.smartForm;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.web.verification.mongodb.MyBaseVerifyO;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfDefault;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfUpdate;
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
 * @see com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMO
 * \
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmartFormDefinitionVerifyO extends MyBaseVerifyO<SmartFormDefinitionMO> {
    @NotBlank(groups = {VerifyGroupOfUpdate.class},message = "[fid]不能为空!")
    private String fid ;

    /**
     * 表单标题
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[标题]不能为空!")
    private String title ;
    /**
     * 表单类型
     */
    @NotNull
    private SmartFormTypeDefinitionMO formType ;

    /**
     * 描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[描述]不能为空!")
    private String description ;

    /**
     * 顺序
     */
    private Integer orderNum ;
    private String remark ;

    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[表单类型]不能为空!")
    private String formTypeId ;




}
