package com.egg.manager.generate.commons.mybatis;


import com.egg.manager.persistence.db.mysql.mapper.common.OperTableInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description:
 * @ClassName: QueryMysqlTableInfoUtil
 * @Author: zhoucj
 * @Date: 2020/10/14 17:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
//@WebAppConfiguration
public class QueryMysqlTableInfoUtil {

    @Autowired
    private OperTableInfoMapper operTableInfoMapper;


    @Test
    public void queryTableNameList() {
        //String sql = "select table_name from information_schema.tables where table_schema='egg_manager';" ;
        ApplicationContext context = new ClassPathXmlApplicationContext("mapper/common/OperTableInfoMapper.xml");
        BeanFactory factory = (BeanFactory) context;
        OperTableInfoMapper customerInfoMapper=factory.getBean(OperTableInfoMapper.class);
        try {
            List<String> tableNames = customerInfoMapper.getAllTableName("egg_manager");
            System.out.println("...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
