package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Profile("!prod")   // !prod == dev or test (개발 혹은 테스트 환경. 운영모드가 아닐경우 실행)
@Configuration
@RequiredArgsConstructor
public class NotProd {

    @Lazy
    @Autowired
    private NotProd self;   // Bean으로 등록됨.
                            // this를 통한 객체 내부에서의 메서드 호출은 @Transactional을 작동시키지 않는다.
                            // 외부객체에 의한 메서드 호출은 @Transactional이 작동한다.
                            // @Lazy, @Autowired 조합은 this의 외부 호출 모드 버전인 self를 얻을 수 있다.
                            // self를 통한 메서드 호출은 @Transactional을 작동 시킬 수 있다.

    private final ArticleRepository articleRepository;

    @Bean   // 개발자가 new 하지 않아도스프링부트가 직접 관리하는 객체
    public ApplicationRunner initNotProd() {

        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {

        // 읽기 전용 트렌잭션.
        if (articleRepository.count() > 0 ) return;   // 테이블에 데이터가 이미 존재한다면 종료.

        // articleRepository.deleteAll();  // 테이블의 데이터 삭제.

        Article article1 = Article.builder().
                title("제목1")
                .body("내용1").build();

        Article article2 = Article.builder().
                title("제목2")
                .body("내용2").build();

        // 쓰기 전용 트렌젝션
        articleRepository.save(article1);
        articleRepository.save(article2);

        article2.setTitle("제목 2-2");

        articleRepository.delete(article1);

    }

    @Transactional
    public void work2() {
        // List : 0 ~ N (넣을 수 있는 값의 개수)
        // Optional : 0 ~ 1
        Optional<Article> opArticle = articleRepository.findById(2L);   // JpaRepository 기본 제공

        List<Article> articles = articleRepository.findAll();   // JpaRepository 기본 제공

    }
}