package com.egg.manager.api.config.db.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.egg.manager.api.config.db.mybatis.plus.methods.FakeDeleteByIdMethod;
import com.egg.manager.api.config.db.mybatis.plus.methods.LogicBatchDeleteWithFillMethod;
import com.egg.manager.api.config.db.mybatis.plus.methods.LogicDeleteWithFillMethod;

import java.util.List;

/**
 * @description:  mybatisplus 增强方法注入器
 * @author zhoucj
 * @date 2020/10/26
 */
public class MybatisPlusInjector extends DefaultSqlInjector {

    /**
     * 返回所有mybatisplus dao 增强的方法配置
     * @param tClass
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> tClass) {
        // 这里很重要，先要通过父类方法，获取到原有的集合，不然会自带的通用方法会失效的
        List<AbstractMethod> methodList = super.getMethodList(tClass);
        //||添加自定义方法类
        //方法-根据id伪删除
        methodList.add(new FakeDeleteByIdMethod());
        methodList.add(new LogicDeleteByIdWithFill());
        //methodList.add(new LogicDeleteWithFillMethod());
        methodList.add(new LogicBatchDeleteWithFillMethod());

        return methodList;
    }

}