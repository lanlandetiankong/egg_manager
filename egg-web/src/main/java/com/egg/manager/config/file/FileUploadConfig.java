package com.egg.manager.config.file;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/16
 * \* Time: 20:53
 * \* Description:
 * \
 */
@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory() ;
        factory.setMaxFileSize("50Mb");
        factory.setMaxRequestSize("100Mb");
        return factory.createMultipartConfig() ;
    }


}
