package com.egg.manager.common.base.props.build.deploy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * \* note: 本地、服务器部署配置文件的映射
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Component
@ConfigurationProperties(prefix = "egg.build.deploy")
@Data
public class DeployConfProps {

    @Value("allowedOrigins")
    private String[] allowedOrigins ;




}
