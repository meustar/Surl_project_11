package com.koreait.surl_project_11.global.springDoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "APP-API", version = "V1")) // swagger 문서 세팅
@SecurityScheme(        // Authorize 헤더로 로그인하는 기능 활성화
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

// Select a definition apiV1 / controller
public class SpringDocConfig {
    @Bean
    public GroupedOpenApi groupApiV1() {    // API v1에 관련된 API에 대한 문서 생성
        return GroupedOpenApi.builder()
                .group("apiV1")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi groupController() {   // API v1에 관련없는 API에 대한 문서 생성
        return GroupedOpenApi.builder()
                .group("controller")
                .pathsToExclude("/api/**")
                .build();
    }
}