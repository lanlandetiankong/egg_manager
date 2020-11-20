package com.egg.manager.persistence.em.user.pojo.verification.login;

import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountVo;
import com.egg.manager.persistence.exchange.bean.webvo.verification.WebVoBaseVerifyO;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoucj
 * @description 登录表单 对应的验证对象
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginAccountVerifyO extends WebVoBaseVerifyO<LoginAccountVo> {
    /**
     * 账号
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[账号]不能为空!")
    private String account;
    /**
     * 密码
     */
    @NotBlank(groups = {VerifyGroupOfDefault.class}, message = "[密码]不能为空!")
    private String password;

}
