package com.ssafy.tripchat.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 프론트엔드 서버 URL 명시적으로 추가
        registry.addMapping("/**")
                .allowedOrigins("http://72.12.107.134:5173")  // 프론트엔드 서버 URL
                .allowedOriginPatterns("*")  // 개발 중에는 모든 오리진 허용
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Authorization", "Content-Type", "Accept", "*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
