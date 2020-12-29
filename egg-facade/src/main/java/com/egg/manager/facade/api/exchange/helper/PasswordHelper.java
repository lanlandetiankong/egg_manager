package com.egg.manager.facade.api.exchange.helper;


import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author zhoucj
 * @description 密码-操作助手
 * @date 2020/11/13
 */
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private final int hashIterations = 2;

    public void encryptPassword(EmUserAccountEntity user) {
        if (StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(user.getSalt())) {
            return;
        }
        user.setSalt(StringUtils.isNotBlank(user.getSalt()) ? user.getSalt() : randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * 判断账号是否匹配
     * @param formPwd             form表单上的password，未经过
     * @param emUserAccountEntity
     * @return
     */
    public boolean isPasswordMatch(String formPwd, EmUserAccountEntity emUserAccountEntity) {
        if (StringUtils.isBlank(formPwd)) {
            return false;
        }
        String md5Pwd = new SimpleHash(
                algorithmName,
                formPwd,
                ByteSource.Util.bytes(emUserAccountEntity.getSalt()),
                hashIterations).toHex();
        return md5Pwd.equalsIgnoreCase(emUserAccountEntity.getPassword());
    }
}