package com.egg.manager.em.web.config.file;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        DataSize minSize = DataSize.ofBytes(50 * 1024L);
        DataSize maxSize = DataSize.ofBytes(100 * 1024L);
        factory.setMaxFileSize(minSize);
        factory.setMaxRequestSize(maxSize);
        return factory.createMultipartConfig();
    }


}
