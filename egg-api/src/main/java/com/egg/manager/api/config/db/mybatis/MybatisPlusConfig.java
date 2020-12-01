package com.egg.manager.api.config.db.mybatis;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"com.egg.manager.persistence.**.db.mysql"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public Page paginationInterceptor() {
        return new Page();
    }

    /**
     * 支持乐观锁
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    /**
     * 自定义sql注入器
     * 或者application.properties配置：
     * mybatis-plus.globalConfig.sqlInjector=com.javasgj.springboot.mybatisplus.config.GeneralMybatisPlusSqlInjector
     */
    @Bean
    public ISqlInjector iSqlInjector() {
        return new MybatisPlusInjector();
    }

}
