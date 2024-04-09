package com.ryan.simpleBlog.dto;

import com.ryan.simpleBlog.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    /**
     * 클라이언트에게 엔티티(DB 데이터)를 반환하는 기능이므로,
     * 엔티티를 인수로 받는다!
     */

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
