package com.egg.manager.generate.mybatis;


import com.egg.manager.persistence.db.mysql.mapper.common.OperTableInfoMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @Description:
 * @ClassName: QueryMysqlTableInfoUtil
 * @Author: zhoucj
 * @Date: 2020/10/14 17:06
 */
public class QueryMysqlTableInfoUtil {




    public static void main(String[] args) {
        //String sql = "select table_name from information_schema.tables where table_schema='egg_manager';" ;
        ApplicationContext context = new ClassPathXmlApplicationContext();
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
