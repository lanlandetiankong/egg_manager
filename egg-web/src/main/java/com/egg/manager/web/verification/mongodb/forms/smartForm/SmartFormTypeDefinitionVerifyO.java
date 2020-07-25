package com.egg.manager.web.verification.mongodb.forms.smartForm;

import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.web.verification.mongodb.MyBaseVerifyO;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfCreate;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfDefault;
import com.egg.manager.web.verification.mongodb.VerifyGroupOfUpdate;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

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
public class SmartFormTypeDefinitionVerifyO extends MyBaseVerifyO<SmartFormTypeDefinitionMO> {
    @NotBlank(groups = {VerifyGroupOfUpdate.class, VerifyGroupOfDefault.class})
    private String id ;

    /**
     * 顺序
     */
    private Integer orderNum ;
    /**
     * 类型名
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class})
    private String name ;
    /**
     * 类型描述
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class})
    private String description ;
    private String remark ;




}
