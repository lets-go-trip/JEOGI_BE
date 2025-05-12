package com.ssafy.tripchat.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TRIP CHAT API")
                        .description("여행 정보를 공유하고 예약하는 서비스")
                        .version("1.0.0")
                );
    }
}