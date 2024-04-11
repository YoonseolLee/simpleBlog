package com.ryan.simpleBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // email로 사용자 정보 가져옴 (쿼리 메소드)
    Optional<User> findByEmail(String email);
}
