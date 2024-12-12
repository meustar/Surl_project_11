package com.koreait.surl_project_11.domain.surl.surl.dto;

import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SurlDto {
    // Entity의 내용중 보여줄 내용에 대해서 Dto로 변환한다.

    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private long authorId;
    @NonNull
    private String authorName;
    @NonNull
    private String body;
    @NonNull
    private String url;
    @NonNull
    private long count;

    public SurlDto(Surl surl) {
        this.id = surl.getId();
        this.createDate = surl.getCreateDate();
        this.modifyDate = surl.getModifyDate();
        this.authorId = surl.getAuthor().getId();
        this.authorName = surl.getAuthor().getName();
        this.body = surl.getBody();
        this.url = surl.getUrl();
        this.count = surl.getCount();
    }
}
