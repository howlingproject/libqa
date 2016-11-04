package com.libqa.application.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
class FileHandlerTestConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public FileHandler fileHandler() {
        return new FileHandler();
    }

}
