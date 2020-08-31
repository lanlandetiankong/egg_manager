package com.egg.manager.common.base.query.mongo;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @Description:
 * @ClassName: MyMongoUpdateBean
 * @Author: zhoucj
 * @Date: 2020/8/19 16:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyMongoUpdateBean<T> {
    /**
     * 更新的文档对象
     */
    private Document document ;
    /**
     * class
     */
    private Class clazz ;


    public static <T> MyMongoUpdateBean getByMongoUpdate(Update update,Class<T> clazz){
        Assert.notNull(update,"请传入要更新的update对象!");
        Assert.notNull(clazz,"请指定要clazz参数!");
        return MyMongoUpdateBean.builder().document(update.getUpdateObject()).clazz(clazz).build();
    }

}
