package com.libqa.config;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class ApplicationConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public HandlebarsViewResolver handlebarsViewResolver() {
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".hbs");
        return viewResolver;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
    }

}
