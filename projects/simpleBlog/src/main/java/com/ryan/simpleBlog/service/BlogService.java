package com.ryan.simpleBlog.service;

import com.ryan.simpleBlog.domain.Article;
import com.ryan.simpleBlog.dto.AddArticleRequest;
import com.ryan.simpleBlog.dto.UpdateArticleRequest;
import com.ryan.simpleBlog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
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

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
