package com.egg.manager.common.util.str;

import java.util.UUID;

public class MyUUIDUtil {
    public static String renderSimpleUuid() {
        return UUID.randomUUID().toString().replace("-","") ;
    }

    public static String renderMd5Password(String password,String salt) {
        password = password != null ? password : "" ;
        salt = salt != null ? salt : "" ;
        //return  DigestUtils.md5DigestAsHex(new String(password+salt).getBytes()) ;
        return "" ;
    }

    /**
     * 验证密码是否正确
     * @param password 前端传递的未加密的密码
     * @param salt 盐
     * @param dbPassword 数据库中的密码
     * @return
     */
    public static boolean varifyMd5PasswordIsTrue(String password,String salt,String dbPassword) {
        boolean flag = false ;
        password = password != null ? password : "" ;
        salt = salt != null ? salt : "" ;
        /*String tempMd5Pwd = DigestUtils.md5DigestAsHex(new String(password+salt).getBytes()) ;
        if(tempMd5Pwd.equals(dbPassword)) {
            flag = true ;
        }*/
        return flag ;
    }

    /**
     * 验证密码是否正确
     * @param password 前端传递的未加密的密码
     * @param
     * @return
     */
    public static boolean varifyMd5PasswordIsTrue(String password) {
        boolean flag = false ;
        password = password != null ? password : "" ;
       // flag = varifyMd5PasswordIsTrue(password,userAccount.getAccount(),userAccount.getPassword()) ;
        return flag ;
    }

}
