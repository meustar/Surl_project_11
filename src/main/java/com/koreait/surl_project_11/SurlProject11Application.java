package com.koreait.surl_project_11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // @EntityListeners(AuditingEntityListener.class)을 쓰기 위한 어노테이션
public class SurlProject11Application {

    public static void main(String[] args) {
        SpringApplication.run(SurlProject11Application.class, args);
    }

}
