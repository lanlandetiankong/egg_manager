package com.egg.manager.generate.test;

import cn.hutool.core.util.ReflectUtil;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

import java.lang.reflect.Method;

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
        ReflectUtil.invoke(userAccount,methodOfObj, MyUUIDUtil.renderSimpleUuid());


        UserAccount userAccount2 = new UserAccount() ;

        handlePojoSetFieldValue(userAccount2,"fidsss",String.class,MyUUIDUtil.renderSimpleUuid());

    }


    public static void handlePojoSetFieldValue(Object obj,String fieldName,Class valClz,Object value) {
        String methodName = "set"+ MyStringUtil.captureFirstWord(fieldName) ;
        Method methodOfObj = ReflectUtil.getMethod(obj.getClass(), methodName, valClz);
        ReflectUtil.invoke(obj,methodOfObj, value);
    }


}
