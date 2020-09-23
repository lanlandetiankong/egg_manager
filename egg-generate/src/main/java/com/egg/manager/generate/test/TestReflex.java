package com.egg.manager.generate.test;

import cn.hutool.core.util.ReflectUtil;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.transfer.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description:
 * @ClassName: TestReflex
 * @Author: zhoucj
 * @Date: 2020/9/18 15:40
 */
public class TestReflex {
    public static void main(String[] args) {
        UserAccount userAccount = new UserAccount() ;
        Method methodOfObj = ReflectUtil.getMethod(UserAccount.class, "setFid", String.class);
        ReflectUtil.invoke(userAccount,methodOfObj, MyUUIDUtil.renderSimpleUUID());


        UserAccount userAccount2 = new UserAccount() ;

        handlePojoSetFieldValue(userAccount2,"fidsss",String.class,MyUUIDUtil.renderSimpleUUID());


        System.out.println("for debug");
    }


    public static void handlePojoSetFieldValue(Object obj,String fieldName,Class valClz,Object value) {
        String methodName = "set"+ MyStringUtil.captureFirstWord(fieldName) ;
        Method methodOfObj = ReflectUtil.getMethod(obj.getClass(), methodName, valClz);
        ReflectUtil.invoke(obj,methodOfObj, value);
    }


}
