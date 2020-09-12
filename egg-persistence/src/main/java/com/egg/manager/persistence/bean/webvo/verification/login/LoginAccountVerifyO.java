package com.egg.manager.persistence.bean.webvo.verification.login;

import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import com.egg.manager.persistence.bean.webvo.verification.WebVoBaseVerifyO;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * \* note: 登录表单 对应的验证对象
 * \* User: zhouchengjie
 * \* Date: 2020/7/25
 * \* Time: 11:06
 * \* Description:
 * \
 */
@Data
public class LoginAccountVerifyO extends WebVoBaseVerifyO<LoginAccountVo> {
    /**
     * 账号
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[账号]不能为空!")
    private String account;
    /**
     * 密码
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class},message = "[密码]不能为空!")
    private String password;

}
