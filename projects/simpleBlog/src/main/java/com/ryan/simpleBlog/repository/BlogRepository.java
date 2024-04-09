package com.ryan.simpleBlog.repository;

import com.ryan.simpleBlog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
