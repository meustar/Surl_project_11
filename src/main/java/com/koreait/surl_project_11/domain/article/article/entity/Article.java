package com.koreait.surl_project_11.domain.article.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity // 이 클래스로 테이블 만들것이다.
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id // 이 필드를 primary Key로 만들것이다.
    @GeneratedValue(strategy = IDENTITY)    // Auto_increment
    private long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
}
