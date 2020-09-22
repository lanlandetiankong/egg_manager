package com.egg.manager.api.config.db;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.egg.manager.persistence.db.mysql.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public Page paginationInterceptor() {
        return new Page();
    }
}
