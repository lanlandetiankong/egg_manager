package com.egg.manager.web.verification.mongodb.forms.smartForm;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.web.verification.mongodb.MyBaseVerifyO;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfDefault;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * \* note: SmartFormTypeDefinitionMO 对应的验证对象
 * \* User: zhouchengjie
 * \* Date: 2020/7/25
 * \* Time: 11:06
 * \* Description:
 * @see SmartFormTypeDefinitionMO
 * \
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmartFormTypeDefinitionVerifyO extends MyBaseVerifyO<SmartFormTypeDefinitionMO> {
    @NotBlank(groups = {VerifyGroupOfUpdate.class},message = "[fid]不能为空!")
    private String fid ;

    /**
     * 顺序
     */
    private Integer orderNum ;
    /**
     * 类型名
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[类型名]不能为空!")
    private String name ;
    /**
     * 类型描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[描述]不能为空!")
    private String description ;
    private String remark ;




}
