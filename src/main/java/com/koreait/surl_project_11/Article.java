package com.koreait.surl_project_11;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity // 이 클래스로 테이블 만들것이다.
public class Article {

    @Id // 이 필드를 primary Key로 만들것이다.
    @GeneratedValue(strategy = IDENTITY)    // Auto_increment
    private long id;
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
}
