package com.ryan.simpleBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {

    /**
     * 블로그 글 수정 요청을 받는 DTO
     */

    private String title;
    private String content;

}
