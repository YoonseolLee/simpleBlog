package com.ryan.simpleBlog.service;

import com.ryan.simpleBlog.domain.Article;
import com.ryan.simpleBlog.dto.AddArticleRequest;
import com.ryan.simpleBlog.dto.UpdateArticleRequest;
import com.ryan.simpleBlog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final 또는 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메소드
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

//    public void delete(long id) {
//        blogRepository.deleteById(id);
//    }

      // 트랜잭션 메서드
//    @Transactional
//    public Article update(long id, UpdateArticleRequest request) {
//        Article article = blogRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
//
//        article.update(request.getTitle(), request.getContent());
//
//        return article;
//    }

    /**
     * OAuth
     * 이제 글을 수정/삭제할 때 요청 헤더에 토큰을 전달하므로
     * 사용자 자신이 작성한 글인지 검증할 수 있다.
     * 따라서 본인 글이 아닌데 수정/삭제를 시도하는 경우에 예외를 발생시킨다.
     */

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
