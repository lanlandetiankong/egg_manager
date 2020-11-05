package com.egg.manager.persistence.commons.util.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author zhoucj
 * @description: spring配置项加密
 * @date 2020/10/21
 */
@Slf4j
public class JasyptUtil {

    /**
     * 必须与application.properties的值一致
     */
    private static String jasyptEncryptorPassword = "EggJasypt";

    public static void main(String[] args) {
        encryptMySqlConfig();
    }

    /**
     * 取得mysql加密后的配置信息
     * 相当于java -cp ./jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI password="ADUMDFUOV7834*" algorithm=PBEWithMD5AndDES input=root
     */
    public static void encryptMySqlConfig() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 加密密钥
        textEncryptor.setPassword(jasyptEncryptorPassword);
        // 要加密的数据（如数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("root");
        log.debug("username:{}", username);
        log.debug("password:{}", password);
    }
}
