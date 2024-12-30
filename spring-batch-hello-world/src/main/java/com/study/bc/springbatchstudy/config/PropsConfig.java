package com.study.bc.springbatchstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropsConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer config() {
        var configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("/etc/config/springbatchhelloworld/application.properties"));
        return configurer;
    }
}
