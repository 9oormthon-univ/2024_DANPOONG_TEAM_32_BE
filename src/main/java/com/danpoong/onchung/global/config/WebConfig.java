package com.danpoong.onchung.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://www.youthmap.site:8080", "http://youthmap.site:8080", "http://localhost:8080", "http://3.36.205.191")
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
