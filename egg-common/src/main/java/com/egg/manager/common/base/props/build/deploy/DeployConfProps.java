package com.egg.manager.common.base.props.build.deploy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * \* note: 本地、服务器部署配置文件的映射
 * @author: zhouchengjie
 * \* Description:
 * \
 */
@Data
@Component
@ConfigurationProperties(prefix = "egg.build.deploy")
public class DeployConfProps  implements Serializable {
    /**
     * 跨域放行的源
     */
    @Value("allowedOrigins")
    private String[] allowedOrigins ;




}
