package com.egg.manager.facade.api.config.db.mybatis.plus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.egg.manager.facade.api.config.db.SnowflakeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: mybatisplus自定义id生成
 * @ClassName: MpCustomIdGenerator
 * @Author: zhoucj
 * @Date: 2020/11/4 9:23
 */
@Slf4j
@Component
public class MpCustomIdGenerator implements IdentifierGenerator {
    private final AtomicLong al = new AtomicLong(1);

    @Autowired
    private SnowflakeConfig snowflakeConfig;

    @Override
    public Long nextId(Object entity) {
        return snowflakeConfig.snowflakeId();
    }

}