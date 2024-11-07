package com.koreait.surl_project_11;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Article {

    @Id // 이거는 primary Key 입니다.
    @GeneratedValue(strategy = IDENTITY)    // Auto_increment
    private long id;
    private String title;
    private String body;
}
