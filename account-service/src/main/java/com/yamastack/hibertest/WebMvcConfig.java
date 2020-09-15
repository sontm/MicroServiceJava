package com.yamastack.hibertest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${app.setting.origin}")
    private String allowOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("-------------allowOrigin-------" + allowOrigin);
        registry.addMapping("/**")
                .allowedOrigins(allowOrigin)
                .allowCredentials(true).maxAge(3600);
    }
}
