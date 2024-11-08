package com.koreait.surl_project_11.domain.article.article.entity;

import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;


@Entity // 이 클래스로 테이블 만들것이다.
@Builder
@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Article extends BaseTime {
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
}
