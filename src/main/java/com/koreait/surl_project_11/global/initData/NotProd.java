package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;


@Profile("!prod")   // !prod == dev or test (개발 혹은 테스트 환경. 운영모드가 아닐경우 실행)
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ArticleRepository articleRepository;

    @Bean   // 개발자가 new 하지 않아도스프링부트가 직접 관리하는 객체
    public ApplicationRunner initNotProd() {

        return new ApplicationRunner() {

            @Override
            @Transactional  // 하나의 단위로 묶음.
            public void run(ApplicationArguments args) throws Exception {

                    // 읽기 전용 트렌잭션.
                    if (articleRepository.count() > 0 ) return;   // 테이블에 데이터가 이미 존재한다면 종료.

//            articleRepository.deleteAll();  // 테이블의 데이터 삭제.

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
        };
    }
}